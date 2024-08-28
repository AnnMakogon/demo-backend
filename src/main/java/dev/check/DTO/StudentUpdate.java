package dev.check.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdate {

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;
    private String departmentName;
    private String course;
}
