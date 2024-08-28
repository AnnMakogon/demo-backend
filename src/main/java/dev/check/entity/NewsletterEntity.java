package dev.check.entity;

import dev.check.entity.EnumEntity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "Newsletters")
@SequenceGenerator(name = "newsletter_seq", sequenceName = "newsletter_seq", allocationSize = 1)
// name - это название генератора для проекта, а sequenceName - для бд
public class NewsletterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsletter_seq")
    private Long id;

    private OffsetDateTime date;
    private String text;
    private String subject;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "newsletter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;

    private Boolean sent;

    @Enumerated(EnumType.STRING)
    private Status status;

}
