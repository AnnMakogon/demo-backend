package dev.check.entity;

import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.Role;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    //todo
    private Integer course;

    //todo переделать на сущности
    @Enumerated(EnumType.STRING)
    private DepartmentName department;

    //todo переделать на сущности
    //3.3000000001
    @Column(name = "group_of_students")
    private Float group;

    @ManyToOne
    @JoinColumn(name = "newsletter_id")
    private NewsletterEntity newsletter;
}
