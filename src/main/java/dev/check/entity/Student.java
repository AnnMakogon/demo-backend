package dev.check.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name="Students")
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
public class Student {

    public String toString(){
        return this.fio + ' ' + this.group + ' ' + this.phoneNumber;
    }

    public Student(Long id, String fio, String group, String phoneNumber) {
        this(fio, group, phoneNumber);
        this.id = id;
    }

    public Student(String fio, String group, String phoneNumber) {
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "student_seq")
    private Long id;
    private String fio;
    @Column(name="group_of_students")
    private String group;
    private String phoneNumber;

}
