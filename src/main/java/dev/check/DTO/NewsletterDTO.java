package dev.check.DTO;

import dev.check.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsletterDTO { //то что приходит из фронта

    private Long id;             //id
    private String date;         // дата
    private String address;        // получатель
    private String text;         // текст письма
    private String subject;      // тема письма
    private String mess;        // отправлено или нет
    private String status;      // статус(в очереди/дошло/не дошло)

}
