package dev.check.manager.SentOnTime;

import dev.check.entity.NewsletterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class SentExecutorOnTime {
    private final JavaMailSender emailSender;
    public void schedulerSendMessage(NewsletterEntity newsletter, String email) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setText(newsletter.getText());
        helper.setSubject(newsletter.getSubject());
        helper.setTo(email);

        emailSender.send(message); // отправка письма

        log.info("Letter " + newsletter.getId() + " ----  Date of letter: " + newsletter.getDate() + ". Thread: " + Thread.currentThread().getName()); // логирование для проверки
    }
}
