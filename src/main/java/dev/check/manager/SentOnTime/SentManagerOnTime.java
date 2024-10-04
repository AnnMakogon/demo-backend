package dev.check.manager.SentOnTime;

import dev.check.dto.Newsletter;
import dev.check.entity.AddressEntity;
import dev.check.entity.NewsletterEntity;
import dev.check.mapper.NewsletterMapper;
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
    private final NewsletterMapper newsletterMapper;
    private final SentExecutorOnTime sentExecutorOnTime;
    private ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasksMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public void sentOnTimeLetter(){
        List<Newsletter> newsletters = newsletterService.getForSentScheduler(10);

        for (Newsletter newsletter : newsletters) {
            NewsletterEntity newsletterEntity = newsletterMapper.newsletterDtoToNewsletter(newsletter);

            long initialDelay = Duration.between(OffsetDateTime.now(), newsletter.getDate()).toMillis(); //время до отправки

            if (initialDelay >= 0) {
                ScheduledFuture<?> future = scheduler.schedule(() -> {
                   this.preparationForSent(newsletterEntity);
                }, initialDelay, TimeUnit.MILLISECONDS);

                scheduledTasksMap.put(newsletter.getId(), future);
            } else {
                log.info("Newsletter with id: " + newsletter.getId() + " has already missed its dispatch time.");
            }
        }
    }
    public void preparationForSent(NewsletterEntity newsletter) {
        long initialDelay = Duration.between(OffsetDateTime.now(), newsletter.getDate()).toMillis() + 25L;

        Runnable task = () -> {
            try {
                NewsletterEntity currentNewsletter = newsletterService.findNewsletterWithAddressesById(newsletter.getId());
                if (currentNewsletter != null) {
                    if (!currentNewsletter.getAddresses().isEmpty()) {
                        for (AddressEntity address : currentNewsletter.getAddresses()) {
                            List<String> emails = newsletterService.getEmailForSent(address.getCourse().getCourseNumber().getCourse(), address.getDepartments().toString(), address.getGroups());
                            for (String email : emails) {
                                sentExecutorOnTime.schedulerSendMessage(currentNewsletter, email);
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

}
