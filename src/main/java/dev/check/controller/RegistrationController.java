package dev.check.controller;

import dev.check.DTO.StudentRegistr;
import dev.check.repositories.UserRepository;
import dev.check.service.NewsletterService;
import dev.check.service.UserRegService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegService userRegService;

    private final NewsletterService emailService;

    private final UserRepository userRepository;

    //todo как делать регистрацию с подтверждением через email
    @PostMapping(value = "/api/base/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistr sendMail(@RequestBody StudentRegistr newStudent) {
        if (userRepository.findUserByName(newStudent.getFio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exist");
        }
        emailService.sendSimpleMessage(newStudent);
        return newStudent;
    }

    // здесь сама регистрация добавление
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/registr", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistr registration(@RequestBody StudentRegistr regStudent) {
        return userRegService.regStudent(regStudent);
    }

}
