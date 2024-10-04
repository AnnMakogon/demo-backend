package dev.check.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student{

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;  // скрыто
    private String course;
    private String departmentName;

}
