package dev.check.controller;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.service.UserRegService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/base")
@Slf4j
@NoArgsConstructor
@Controller
public class RegistrationController {

    @Autowired
    private UserRegService userRegService;

    @PostMapping(value = "registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentRegistrDTO registration(@RequestBody StudentRegistrDTO newStudent) {
        log.info("REGISTRATION");
        return userRegService.regStudent(newStudent);
    }
}
