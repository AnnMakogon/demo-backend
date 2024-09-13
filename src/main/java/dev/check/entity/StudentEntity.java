package dev.check.entity;

import dev.check.entity.EnumEntity.DepartmentName;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Table(name = "Students")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
public class StudentEntity {

    public String toString() {
        return this.fio + ' ' + this.group + ' ' + this.phoneNumber;
    }

    public StudentEntity(String fio, Float group, String phoneNumber, Integer course, DepartmentName dep, UserEntity u) {
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.departmentName = dep;
        this.user = u;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    private Long id;
    private String fio;
    @Column(name = "group_of_students")
    private Float group;
    private String phoneNumber;
    private Integer course;

    @Enumerated(EnumType.STRING)
    private DepartmentName departmentName;

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

}
