package dev.check.manager.SentOnTime;

import dev.check.dto.Newsletter;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.CourseEntity;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SentManagerOnTime {
    private final NewsletterService newsletterService;
    private final SentExecutorOnTime sentExecutorOnTime;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public void sentOnTimeLetter(){
        newsletterService.setAllStatusNotSent();
        List<Newsletter> newsletters = newsletterService.getForSentScheduler(10);// взяли с бд первых 10 не отправленных писем

        if (newsletters.isEmpty()){
            log.info("All Letters have been sent");
            return ;
        }
        for (Newsletter newsletter : newsletters) {

            newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);
            long initialDelay = Duration.between(OffsetDateTime.now(), newsletter.getDate()).toMillis();

            Runnable task = () -> {
                NewsletterEntity currentNewsletter = newsletterService.findNewsletterWithAddressesById(newsletter.getId());
                if (currentNewsletter != null && !currentNewsletter.getAddresses().isEmpty()) {
                    log.info("Проверка таски 1");

                    currentNewsletter.getAddresses().stream()
                            .flatMap(address -> address.getCourses().stream()
                                    .flatMap(addressCourse -> {
                                        log.info("Проверка таски 3");
                                        List<String> emails = newsletterService.getEmailForSent(
                                                addressCourse.getCourse().getCourseNumber().toString(),
                                                address.getDepartments(),
                                                address.getGroups()
                                        );
                                        log.info("Проверка таски 4");
                                        return emails.stream();
                                    })
                            )
                            .forEach(email -> {
                                log.info("Проверка таски 5");
                                try {
                                    sentExecutorOnTime.schedulerSendMessage(currentNewsletter, email);
                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }
                                newsletterService.changeStatus(newsletter.getId(), Status.SUCCESSFULLY);
                            });
                }
            };
            scheduler.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
        }

        OffsetDateTime lastSendingTime = newsletters.get(newsletters.size()-1).getDate();
        if (lastSendingTime != null){
            long nextIterationDelay = Duration.between(OffsetDateTime.now(), lastSendingTime).toMillis();
            scheduler.schedule(this::sentOnTimeLetter, nextIterationDelay,TimeUnit.MILLISECONDS);
        }
    }
}
