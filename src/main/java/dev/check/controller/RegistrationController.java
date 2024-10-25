package dev.check.controller;

import dev.check.dto.StudentRegistr;
import dev.check.dto.User;
import dev.check.entity.UserEntity;
import dev.check.service.NewsletterService;
import dev.check.service.UserRegService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegService userRegService;

    private final NewsletterService emailService;

    // сама регистрация с добавлением юзера и студента с enableEmail = false
    @PostMapping(value = "/api/base/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistr registrStudent(@Validated @RequestBody StudentRegistr newStudent){
        userRegService.regStudent(newStudent);
        return newStudent;
    }

    // подтверждение по кнопке
   @PostMapping(value = "/api/base/confirmation", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMail(@Validated @RequestBody User newStudent) { //должно возвращать дто регистрации для фронта
        emailService.sendRegistrMessage(newStudent);
    }

    // ссылка из письма ведет на фронт, откуда - сюда, здесь обновление в бд с enableEmail = true
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/registr", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User registration(@RequestBody Long id) {
        return userRegService.confirmation(id);
    }

}
