package dev.check.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@lombok.NonNull
public class StudentUpdate {

    private Long id;

    private String fio;

    private String group;

    private String phoneNumber;

    private String departmentName;

    private String course;
}
