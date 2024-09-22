package dev.check.entity;

import dev.check.entity.EnumEntity.CourseNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "students")
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
public class StudentEntity {

    public String toString() {
        return this.fio + ' ' + this.group + ' ' + this.phoneNumber;
    }

    public StudentEntity(Long id, String fio, GroupEntity group, String phoneNumber, Integer course, DepartmentEntity dep, UserEntity u) {
        this.id = id;
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
        this.course = new CourseEntity(null, CourseNumber.valueOf("COURSE_"+course));
        this.department = dep;
        this.user = u;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    private Long id;
    private String fio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    private CourseEntity course;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

}
