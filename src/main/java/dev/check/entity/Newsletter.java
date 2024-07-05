package dev.check.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "newsletter_seq", sequenceName = "newsletter_seq", allocationSize = 1)
public class Newsletter {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "newsletter_seq")
    private Long id;             // id
    private String date;         // дата
    //private String address;      // получатель
    private String text;         // текст письма
    private String subject;      // тема письма
    private Boolean mess;        // отправлено или нет
    private Boolean status;      // статус(дошло/не дошло в случае ошибки)

}
