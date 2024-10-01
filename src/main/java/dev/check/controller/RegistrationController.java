package dev.check.controller;

import dev.check.dto.StudentRegistr;
import dev.check.service.NewsletterService;
import dev.check.service.UserRegService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegService userRegService;

    private final NewsletterService emailService;

    // вначале идет сюда, отсюда посылается письмо на почту для подтверждения
    @PostMapping(value = "/api/base/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistr sendMail(@RequestBody StudentRegistr newStudent) {
        emailService.sendRegistrMessage(newStudent);
        return newStudent;
    }

    // ссылка из письма ведет сюда, здесь сама регистрация-добавление
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/registr", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistr registration(@RequestBody StudentRegistr regStudent) {
        return userRegService.regStudent(regStudent);
    }

}
