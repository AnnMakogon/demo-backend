package dev.check.controller;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.service.EmailService;
import dev.check.service.UserRegService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/base")
@Slf4j
@NoArgsConstructor
@Controller
public class RegistrationController {
    public static final Object lock = new Object();
    @Getter
    public static volatile boolean proof = false;

    @Autowired
    private UserRegService userRegService;
    @Autowired
    private EmailService emailService;

    private StudentRegistrDTO regStudent;


    @PostMapping(value = "/api/base/registration", produces = MediaType.APPLICATION_JSON_VALUE) // отправка письма только
    public void sendMail(@RequestBody StudentRegistrDTO newStudent) {
        regStudent = newStudent;
        log.info("REGISTRATION");
        emailService.sendSimpleMessage(newStudent.getEmail(), "Регистрация", "РЕГИСТРАЦИЯ ");
    }

    @GetMapping(value = "api/registr", produces = MediaType.APPLICATION_JSON_VALUE) // здесь сама регистрация добавление
    public StudentRegistrDTO registration(){
        return userRegService.regStudent(regStudent);
    }

}
