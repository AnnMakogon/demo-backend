package dev.check.manager.SimpleSent;

import dev.check.entity.AddressEntity;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
//@PropertySource("classpath:email.yml")
public class SentManager {

    @Autowired
    private SentExecutor sentExecutor;

    @Autowired
    private NewsletterService newsletterService;

    @Value("${sentManagers.defaultAddress}")
    private String defaultAddress;

    //сама отправка
    //@Scheduled(fixedRate = 180000) // 3 мин // ЗАКОMМЕНТИЛА ЧТОБЫ НЕ ОТПРАВЛЯЛО ПИСЬМА
    public void schedulerSent() throws MessagingException {
        // получение из сервиса-бд списка первых 10 писем со статусом НЕОТПРАВЛЕНО

        //todo сервис должен возвращать только dto
        List<NewsletterEntity> newsletters = newsletterService.getForSentScheduler(10);
        log.info("Size of newsletters is: " + newsletters.size());

        // для каждой из них берется адрес, по адресу через сервис-бд ищется юзер и для каждого юзера из рассылки отправляется письмо
        for (NewsletterEntity newsletter : newsletters) {
            if (!newsletters.isEmpty()) {
                newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);

                if (newsletter.getAddresses().isEmpty()) {
                    sentExecutor.schedulerSendMessage(newsletter, defaultAddress);
                } else {
                    for (AddressEntity address : newsletter.getAddresses()) {

                        log.info("Size of addresses: " + newsletter.getAddresses().size());
                        List<String> emails = newsletterService.getEmailForSent(address.getCourse(),
                                address.getDepartment().toString(),
                                address.getGroup()
                        );

                        log.info("Size of emails: " + emails.size());
                        for (String email : emails) {

                            sentExecutor.schedulerSendMessage(newsletter, email);
                        }
                    }
                }
            }
        }
    }
}
