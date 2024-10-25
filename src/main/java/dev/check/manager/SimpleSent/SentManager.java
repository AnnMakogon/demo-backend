package dev.check.manager.SimpleSent;

import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.EnumEntity.Status;
import dev.check.mapper.NewsletterMapper;
import dev.check.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class SentManager {

    private final SentExecutor sentExecutor;

    private final NewsletterService newsletterService;

    private final NewsletterMapper nlMapper;

    @Value("${sentManagers.defaultAddress}")
    private String defaultAddress;

    //сама отправка
    //@Scheduled(fixedRate = 180000) // 3 мин // ЗАКОMМЕНТИЛА ЧТОБЫ НЕ ОТПРАВЛЯЛО ПИСЬМА
    public void schedulerSent() throws MessagingException {
        // получение из сервиса-бд списка первых 10 писем со статусом НЕОТПРАВЛЕНО
        List<Newsletter> newsletters = new CopyOnWriteArrayList<>(newsletterService.getForSentScheduler(10));
        log.info("Size of newsletters is: " + newsletters.size());

        // для каждой из них берется адрес, по адресу через сервис-бд ищется юзер и для каждого юзера из рассылки отправляется письмо
        for (Newsletter newsletter : newsletters) {
            if (!newsletters.isEmpty()) {
                newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);

                if (newsletter.getAddress().isEmpty()) {
                    sentExecutor.schedulerSendMessage(newsletter, defaultAddress);
                } else {
                    for (Address address : newsletter.getAddress()) {
                        AddressEntity addressEntity = nlMapper.addressToAddressEntity(address);

                        log.info("Size of addresses: " + newsletter.getAddress().size());
                        for (AddressCourseEntity addressCourse : addressEntity.getCourses()) {
                            List<String> emails = newsletterService.getEmailForSent(addressCourse.getCourse().getCourseNumber().getCourse(),
                                    null,//address.getDepartments(), заглушка
                                    addressEntity.getGroups()
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
}
