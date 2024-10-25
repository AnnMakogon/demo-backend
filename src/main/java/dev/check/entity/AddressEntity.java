package dev.check.entity;

import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
@SequenceGenerator(name = "add_seq", sequenceName = "add_seq", allocationSize = 1)
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "add_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<AddressCourseEntity> courses;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<AddressGroupEntity> groups;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<AddressDepartmentEntity> departments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "newsletter_id")
    private NewsletterEntity newsletter;

    public AddressEntity( Role role){
        this.id = null;
        this.role = role;
        this.courses = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.newsletter = null;
    }
}
