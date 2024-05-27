package dev.check.dto;

import dev.check.entity.Student;
import lombok.Getter;

@Getter
public class StudentDto {
    private Long id;
    @Getter
    private String fio;
    @Getter
    private String group;
    @Getter
    private String phoneNumber;
    public void setId(Student student) {
        this.id = student.getId();
    }

    public void setFio(Student student) {
        this.fio = student.getFio();
    }

    public void setGroup(Student student) {
        this.group = student.getGroup();
    }

    public void setPhoneNumber(Student student) {
        this.phoneNumber = student.getPhoneNumber();
    }
}
