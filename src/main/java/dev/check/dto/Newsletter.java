package dev.check.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Newsletter {

    private Long id;
    @NotNull
    private OffsetDateTime date;
    private String text = "";
    private String subject = "";
    private List<Address> address;
    private Boolean sent = false; // отправлено или нет
    private String status = "";  // дошло / есть ошибка / принято в обработку / не отправлено
}