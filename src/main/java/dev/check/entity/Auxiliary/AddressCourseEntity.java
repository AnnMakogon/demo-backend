package dev.check.entity.Auxiliary;

import dev.check.entity.AddressEntity;
import dev.check.entity.CourseEntity;
import dev.check.entity.DepartmentEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "addresses_courses")
@SequenceGenerator(name = "addCourse_seq", sequenceName = "addCourse_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class AddressCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addCourse_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private CourseEntity course;
}
