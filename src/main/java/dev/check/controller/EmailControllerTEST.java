package dev.check.controller;

import dev.check.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class EmailControllerTEST {
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "messtest", produces = MediaType.APPLICATION_JSON_VALUE)  // для теста
    public void sendMess(/* аргументы, которые постятся*/){
        emailService.sendSimpleMessage("myworkemail033@mail.ru", "Proverka", "AAAAAAA");
        System.out.println("send mess on my email");
    }





}
