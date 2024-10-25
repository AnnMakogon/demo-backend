package dev.check.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistr {

    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;
    private String passwordId;
    private String role;
    private boolean enable = false;
    @NotNull
    private String email;
    private String departmentName;
    private String course;

}
