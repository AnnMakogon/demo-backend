package dev.check.entity;

import dev.check.entity.EnumEntity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "newsletters")
@SequenceGenerator(name = "newsletter_seq", sequenceName = "newsletter_seq", allocationSize = 1)
public class NewsletterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsletter_seq")
    private Long id;

    @Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime date;
    private String text;
    private String subject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<AddressEntity> addresses = new ArrayList<>();

    private Boolean sent;

    @Enumerated(EnumType.STRING)
    private Status status;
}
