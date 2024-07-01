package dev.check.service;

import dev.check.DTO.NewsletterDTO;
import dev.check.entity.Newsletter;
import dev.check.entity.Role;
import dev.check.entity.User;
import dev.check.mapper.NlMapper;
import dev.check.repositories.NewsletterRepository;
import dev.check.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@EnableScheduling
@Service("EmailService")
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsletterRepository nlRepository;

    @Autowired
    private NlMapper nlMapper;

    private final TaskScheduler scheduler;
    public EmailService() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        this.scheduler = threadPoolTaskScheduler;  // инициализация в конструкторе
    }
    //это для регистрации подтверждение
    public void sendSimpleMessage( String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            String url = "http://localhost:4200/api/registr";
            String textHolder = "подтверждение";
            helper.setText("<html><body>Здравствуйте, <br><br>Пожалуйста, перейдите по ссылке: <a href='" + url + "'>" + textHolder + "</a></body></html>", true);

            emailSender.send(message);
            System.out.println("send messssssss");
        } catch(MessagingException exception) {
            exception.printStackTrace();
        }
    }


    //отправка самого письма с датой
    public void messNewsletter(NewsletterDTO nl){
        Logger logger = LoggerFactory.getLogger(EmailService.class);
        Newsletter newsletter = nlMapper.newsletterDTOTonewsletter(nl);


        String dateString = nl.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); //создается формат
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        List<User> users = userRepository.getAllUsers();
        for (User u : users) {
            if (u.getRole() != Role.ADMIN) {
                scheduler.schedule(() -> {
                    MimeMessage message = emailSender.createMimeMessage();
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                        helper.setText(nl.getText());
                        helper.setSubject(nl.getSubject());
                        helper.setTo(u.getEmail());
                        emailSender.send(message);
                        logger.info("письмо успешно отправлено к :" + u.getEmail()); //для проверки
                        newsletter.setStatus(true);       //todo запись статуса
                        synchronized (newsletter){
                            newsletter.setMess(true);
                            nlRepository.save(newsletter);
                        }
                    } catch (MessagingException exception) {
                        exception.printStackTrace();
                        logger.error("Не отправлено к ((:" + u.getEmail());
                        newsletter.setStatus(false);      //todo запись статуса
                        synchronized (newsletter){
                            newsletter.setMess(false);
                            nlRepository.save(newsletter);
                        }
                    }

                    nlRepository.save(newsletter);  //сохранение в бд
                }, java.util.Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
            }
        }
        //todo сделать через какое-то время перезаписать mess, когда отправляется
    }

    // удаление
    public Long deleteNl(Long id) {
        nlRepository.deleteById(id);
        return id;
    }

    // подностью переделать или частично
    public NewsletterDTO update(NewsletterDTO nlDto){
        if(nlDto.getId() == null){
            throw new RuntimeException("id of changing nl cannot be null");
        }
        Newsletter nl = nlMapper.newsletterDTOTonewsletter(nlDto);
        Newsletter changingNl = StreamSupport.stream(nlRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()).stream()
                .filter(el -> Objects.equals(el.getId(), nl.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("newsletter with id: " + nl.getId() + "was not found"));
        changingNl.setId(nlDto.getId());
        changingNl.setDate(nlDto.getDate());
        changingNl.setAddress(nl.getAddress());
        changingNl.setText(nl.getText());
        changingNl.setSubject(nl.getSubject());
        changingNl.setMess(nl.getMess());
        changingNl.setStatus(nl.getStatus());

        nlRepository.save(changingNl);
        return nlDto;
    }

    //передатировать
    public NewsletterDTO dataUpdate(NewsletterDTO nlDto){
        if(nlDto == null){
            throw new RuntimeException("id of changing student cannot be null");
        }

        //Newsletter nl = nlRepository.findById(Long.valueOf(nlDtoId)).get();
        Newsletter nl = nlMapper.newsletterDTOTonewsletter(nlDto);
        Newsletter changingNl = StreamSupport.stream(nlRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()).stream()
                .filter(el -> Objects.equals(el.getId(), nl.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("newalatter with id: " + nl.getId() + "was not found"));
        changingNl.setId(nl.getId());
        changingNl.setDate(nl.getDate());
        changingNl.setAddress(nl.getAddress());
        changingNl.setText(nl.getText());
        changingNl.setSubject(nl.getSubject());
        changingNl.setMess(nl.getMess());
        changingNl.setStatus(nl.getStatus());

        nlRepository.save(changingNl);
        return nlDto;
    }
    //получение всего списка
    public List<NewsletterDTO> getNl(String filter, Pageable pageable, boolean showflag) {
        List<Newsletter> newsletters = new ArrayList<Newsletter>();
        if (showflag) {   // если передалось тру - показываем только непрочитанные
            newsletters = nlRepository.getUnreadNl(filter, pageable);
        }else{
            newsletters = nlRepository.getNewsletter(filter, pageable);
        }
        List<NewsletterDTO> newsletterDTOs = nlMapper.newsletterListTonewsletterDtoList(newsletters);

        return new ArrayList<>(newsletterDTOs);
    }

    public Number getLengthStudents(String filter){
        return nlRepository.getLengthStudents(filter);
    }



}






