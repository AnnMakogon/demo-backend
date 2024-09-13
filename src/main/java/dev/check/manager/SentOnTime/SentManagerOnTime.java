package dev.check.manager.SentOnTime;

import dev.check.DTO.Newsletter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
//@PropertySource("classpath:email.yml")
public class SentManagerOnTime {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NewsletterService newsletterService;

    @Value("${sentManagerOnTime.size}")
    private int size;

    @Value("${sentManagers.defaultAddress}")
    private String defaultAddress;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private ConcurrentMap<Long, NewsletterEntity> newsletters = new ConcurrentHashMap<>();

    private AtomicInteger currentIndex = new AtomicInteger(0);

    public void scheduleNewsletter() {
        List<NewsletterEntity> newslettersList = newsletterService.getForSentScheduler(size);
        if (newslettersList.isEmpty()) {
            log.info("All letter have been sent");
            return;
        }
        for (NewsletterEntity newsletter : newslettersList) {
            newsletterService.changeStatus(newsletter.getId(), Status.INPROCESSING);
            newsletters.put(newsletter.getId(), newsletter);
        }
        log.info("Newsletters size = " + newsletters.size());

        processNextLatter();
    }

    private void processNextLatter() {
        List<NewsletterEntity> queue = new ArrayList<>();
        long index = currentIndex.getAndAdd(size);
        for (int i = 0; i < 5 && index <= newsletters.size(); i++) {
            NewsletterEntity newsletter = newsletters.get(index + i);
            if (newsletters.get(index + i) != null) {
                queue.add(newsletter);
            }
        }
        log.info("Queue[1]: " + queue.get(0).getId());

        if (queue.isEmpty()) {
            log.info("All letter have been sent");
            return;
        }

        for (NewsletterEntity newsletter : queue) {
            scheduleNewsletterSend(newsletter);
        }

        scheduler.schedule(this::scheduleNewsletter, calculateTime(queue), TimeUnit.MILLISECONDS);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) scheduler;
        log.info("Size of the scheduler: " + executor.getPoolSize());

        log.info("Next " + size + " letters in the queue, queue[1].id = " + queue.get(0).getId());
    }

    private long calculateTime(List<NewsletterEntity> queue) {
        NewsletterEntity last = queue.get(queue.size() - 1);
        OffsetDateTime sendTime = last.getDate();
        OffsetDateTime now = OffsetDateTime.now();
        long initialDelay = Duration.between(now, sendTime).toMillis();

        if (initialDelay < 0) {
            initialDelay = 0;
        }

        log.info("Time: " + initialDelay);
        return initialDelay + 100;
    }

    private void scheduleNewsletterSend(NewsletterEntity newsletter) {
        OffsetDateTime sendTime = newsletter.getDate();
        long initialDelay = Duration.between(OffsetDateTime.now(), sendTime).toMillis();

        if (initialDelay < 0) {
            log.info("Dispatch time for letter: --" + newsletter.getId() + "-- has already passed");
            return;
        }

        Runnable task = () -> {
            try {
                NewsletterEntity currentNewsletter = newsletterService.findById(newsletter.getId());
                if (currentNewsletter != null) {
                    if (currentNewsletter.getAddresses().isEmpty()) {
                        schedulerSendMessage(currentNewsletter, defaultAddress);
                    } else {
                        for (AddressEntity address : currentNewsletter.getAddresses()) {

                            log.info("Size of addresses: " + currentNewsletter.getAddresses().size());
                            List<String> emails = newsletterService.getEmailForSent(address.getCourse(), address.getDepartment().toString(), address.getGroup());
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

    //имея рассылку и адрес, уже отправляем
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

    // переделывание полностью
    public Newsletter updateInQueue(Newsletter nlDto) {
        Newsletter updateNewsletter = newsletterService.update(nlDto);
        if (updateNewsletter != null) {
            NewsletterEntity updatedEntity = newsletterService.findById(nlDto.getId());
            if (updatedEntity != null) {
                newsletters.put(updatedEntity.getId(), updatedEntity);
                log.info("Update newsletter with id: " + nlDto.getId());
            } else {
                log.warn("Newsletter with id: " + nlDto.getId() + " not found in the queue");
            }
        }
        return nlDto;
    }

    // удаление
    public Long deleteNl(Long id) {
        newsletterService.deleteNl(id);
        boolean removed = newsletters.remove(id) != null;
        if (removed) {
            log.info("Deleted newsletter with id: " + id);
        } else {
            log.warn("Newsletter with id: " + id + " not found in the queue");
        }
        return id;
    }

    // переделывание даты
    public Newsletter dateUpdate(Newsletter nlDto) {
        NewsletterEntity changingNl = newsletterService.dateUpdate(nlDto);
        newsletters.put(changingNl.getId(), changingNl);
        return nlDto;
    }

    //создание
    public List<Long> createNewsletter(Newsletter nl) {
        List<Long> idList = new ArrayList<>();
        newsletterService.createNewsletter(nl).forEach(newsletter -> {
            newsletters.put(newsletter.getId(), newsletter);
            idList.add(newsletter.getId());
        });
        return idList;
    }
}
