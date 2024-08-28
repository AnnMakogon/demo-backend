package dev.check.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFullTable {

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;  // скрыто
    private Integer course;
    private String departmentName;

}
