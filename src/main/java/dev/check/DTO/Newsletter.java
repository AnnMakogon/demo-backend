package dev.check.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Newsletter {

    private Long id;


    private OffsetDateTime date;
    private String text;
    private String subject;
    private List<Address> address;
    private Boolean sent; // отправлено или нет
    private String status;  // дошло / есть ошибка / принято в обработку
}