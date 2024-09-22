package dev.check.entity;

import dev.check.entity.EnumEntity.CourseNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}
