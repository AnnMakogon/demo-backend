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

    //todo рассмотреть вариант использования миграции (liquibase)
    public static void main(String[] args) throws MessagingException {
        ApplicationContext context = SpringApplication.run(CheckApplication.class, args);
        initiator.initial();
        //SentManager sentManager = context.getBean(SentManager.class);
        //sentManager.schedulerSent();// ЗАКОMМЕНТИЛА ЧТОБЫ НЕ ОТПРАВЛЯЛО ПИСЬМА

        SentManagerOnTime scheduler = context.getBean(SentManagerOnTime.class);
        scheduler.scheduleNewsletter();
    }
}