package dev.check.manager;

import dev.check.entity.AddressEntity;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.service.InitializerService;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@EnableScheduling
public class SentManager {

    @Autowired
    private SentExecutor sentExecutor;

    @Autowired
    private NewsletterService newsletterService;

    public static List<NewsletterEntity> newsletters;

    //сама отправка
    @Scheduled(fixedRate = 180000) // 3 мин
    public void schedulerSent() throws MessagingException {
        // получение из сервиса-бд списка первых 10 писем со статусом НЕОТПРАВЛЕНО
        newsletters = newsletterService.getForSentScheduler();
        log.info("Size of newsletters is: " + newsletters.size());

        // для каждой из них берется адрес, по адресу через сервис-бд ищется юзер и для каждого юзера из рассылки отправляется письмо
        for (NewsletterEntity newsletter : newsletters) {
            if (!newsletters.isEmpty()) {
                newsletter.setStatus(Status.INPROCESSING);
                newsletterService.save(newsletter);
                if ( newsletter.getAddresses().isEmpty()){
                    sentExecutor.schedulerSendMessage(newsletter, "myworkemail033@mail.ru");
                }else {
                    for (AddressEntity address : newsletter.getAddresses()) {

                        log.info("Size of addresses: " + newsletter.getAddresses().size());
                        List<String> emails = newsletterService.getNlForSent(address.getCourse(), address.getDepartment().toString(), address.getGroup());
                        log.info("Size of emails: " + emails.size());
                        for (String email : emails) {

                            sentExecutor.schedulerSendMessage(newsletter, email);
                        }
                    }
                }
            }
        }
    }
    // и, имея рассылку и адрес, уже отправляем - в отдельном бине для возможности использовать Async
}
