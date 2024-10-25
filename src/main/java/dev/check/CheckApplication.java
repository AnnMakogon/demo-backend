package dev.check;

import dev.check.manager.SentOnTime.SentManagerOnTime;
import dev.check.service.InitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.mail.MessagingException;

@SpringBootApplication
public class CheckApplication  {
    @Autowired
    private static InitializerService initiator;

    @Autowired
    public void setInitiatorLoader(InitializerService initiator) {
        CheckApplication.initiator = initiator;
    }

    public static void main(String[] args) throws MessagingException {
        ApplicationContext context = SpringApplication.run(CheckApplication.class, args);
        //initiator.initial();

        SentManagerOnTime scheduler = context.getBean(SentManagerOnTime.class);
        scheduler.sentOnTimeLetter();
    }
}