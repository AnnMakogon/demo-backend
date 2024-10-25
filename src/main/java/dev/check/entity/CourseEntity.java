package dev.check.entity;

import dev.check.entity.EnumEntity.CourseNumber;
import dev.check.entity.EnumEntity.DepartmentName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "courses")
@SequenceGenerator(name = "c_seq", sequenceName = "c_seq", allocationSize = 1)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CourseNumber courseNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    public CourseEntity(CourseNumber num){
        this.courseNumber = num;
        this.students = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

}
