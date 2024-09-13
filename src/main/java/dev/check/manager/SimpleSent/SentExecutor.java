package dev.check.manager.SimpleSent;

import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalTime;

@Slf4j
@Component
public class SentExecutor {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NewsletterService newsletterService;

    @Async
    public void schedulerSendMessage(NewsletterEntity newsletter, String email) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setText(newsletter.getText());
        helper.setSubject(newsletter.getSubject());
        helper.setTo(email);

        emailSender.send(message); // отправка письма

        newsletterService.changeStatus(newsletter.getId(), Status.SUCCESSFULLY);

        log.info("Letter " + newsletter.getId() + " ----  Now: {}. Thread: {}", LocalTime.now(), Thread.currentThread().getName()); // логирование для проверки
    }
}
