package dev.check.manager.SentOnTime;

import dev.check.entity.AddressEntity;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SentExecutorOnTime {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NewsletterService newsletterService;

    @Value("${sentManagers.defaultAddress}")
    private String defaultAddress;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    //подготовка, парсинг для отправки
    public void scheduleNewsletterSend(NewsletterEntity newsletter) {
        OffsetDateTime sendTime = newsletter.getDate();
        long initialDelay = Duration.between(OffsetDateTime.now(), sendTime).toMillis() + 25L;

        log.info("Newsletter`s time id: " + newsletter.getId() + " -- " + sendTime + " now: " + OffsetDateTime.now() + " initialDelay = " + initialDelay );

        if (initialDelay < 0) {
            log.info("Dispatch time for letter: --" + newsletter.getId() + "-- has already passed");
            return;
        }

        Runnable task = () -> {
            try {
                NewsletterEntity currentNewsletter = newsletterService.findNewsletterWithAddressesById(newsletter.getId());
                if (currentNewsletter != null) {
                    if (currentNewsletter.getAddresses().isEmpty()) {
                        schedulerSendMessage(currentNewsletter, defaultAddress);
                    } else {
                        for (AddressEntity address : currentNewsletter.getAddresses()) {

                            log.info("Size of addresses: " + currentNewsletter.getAddresses().size());
                            List<String> emails = newsletterService.getEmailForSent(address.getCourse().getCourseNumber().getCourse(), address.getDepartments().toString(), address.getGroups());
                            log.info("Size of emails: " + emails.size());
                            for (String email : emails) {
                                schedulerSendMessage(currentNewsletter, email);
                            }
                        }
                    }
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        };
        scheduler.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
    }

    //само отправление
    private void schedulerSendMessage(NewsletterEntity newsletter, String email) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setText(newsletter.getText());
        helper.setSubject(newsletter.getSubject());
        helper.setTo(email);

        emailSender.send(message); // отправка письма

        newsletter.setStatus(Status.SUCCESSFULLY);
        newsletter.setSent(true);
        newsletterService.save(newsletter);

        log.info("Letter " + newsletter.getId() + " ----  Date of letter: " + newsletter.getDate() + ". Thread: " + Thread.currentThread().getName()); // логирование для проверки
    }
}
