package dev.check.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//todo название непонятное, как будто внутри список или таблица
public class StudentFullTable {

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;  // скрыто
    private String course;
    private String departmentName;

}
