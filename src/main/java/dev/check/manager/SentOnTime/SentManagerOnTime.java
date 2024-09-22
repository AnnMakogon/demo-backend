package dev.check.manager.SentOnTime;

import dev.check.dto.Newsletter;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.mapper.NewsletterMapper;
import dev.check.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
public class SentManagerOnTime {
    @Autowired
    private NewsletterService newsletterService;

    private ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasksMap = new ConcurrentHashMap<>();
    @Autowired
    private SentExecutorOnTime sentExecutorOnTime;

    @Autowired
    private NewsletterMapper newsletterMapper;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    //получение и установление статуса
    public void scheduleNewsletter(){
        List<Newsletter> newslettersList = newsletterService.getForSentScheduler(10);
        newslettersList.forEach( newsletter -> {
            newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);
        });

        scheduleNewsletterSent(newslettersList);
    }

    //закидывание в очередь
    private void scheduleNewsletterSent(List<Newsletter> newsletters){
        for (Newsletter newsletter : newsletters) {
            NewsletterEntity newsletterEntity = newsletterMapper.newsletterDtoToNewsletter(newsletter);

            OffsetDateTime sendTime = newsletter.getDate();

            long initialDelay = Duration.between(OffsetDateTime.now(), sendTime).toMillis();

            if (initialDelay >= 0) {
                ScheduledFuture<?> future = scheduler.schedule(() -> {

                    log.info("Send newsletter with id: " + newsletter.getId());

                    sentExecutorOnTime.scheduleNewsletterSend(newsletterEntity);
                }, initialDelay, TimeUnit.MILLISECONDS);

                scheduledTasksMap.put(newsletter.getId(), future);
            } else {
                log.info("Newsletter with id: " + newsletter.getId() + " has already missed its dispatch time.");
            }
        }
    }

    public void checkQueue(NewsletterEntity newNewsletter){

        boolean isScheduled = scheduledTasksMap.containsKey(newNewsletter.getId());

        long newNewsletterTime = Duration.between(OffsetDateTime.now(), newNewsletter.getDate()).toMillis();

        List<Newsletter> newsletters = newsletterService.getForSentScheduler(10);

        if (newsletters.isEmpty()){
            log.info("No newsletters for schedule");
            return;
        }

        long lastScheduledTime = Duration.between(OffsetDateTime.now(), newsletters.get(newsletters.size()).getDate()).toMillis();

        if(isScheduled || newNewsletterTime < lastScheduledTime) {
            cancelAllScheduledTasks();

            newsletters.forEach(newsletter -> {
                newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);
            });

            scheduleNewsletterSent(newsletters);

            log.info("Queue has been update");
        }else{
            log.info("No need to update of the queue");
        }
    }

    public void checkQueueDelete(Long id){
        NewsletterEntity newsletter = newsletterService.findById(id);

        checkQueue(newsletter);
    }

    private void cancelAllScheduledTasks() {
        for (ScheduledFuture<?> future : scheduledTasksMap.values()) {
            future.cancel(false);
        }
        scheduledTasksMap.clear();
    }
}
