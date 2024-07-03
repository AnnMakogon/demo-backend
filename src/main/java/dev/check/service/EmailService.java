package dev.check.service;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @Autowired
    private SimpMessagingTemplate template;

    private final TaskScheduler scheduler;
    //private String dateNl;

    private NewsletterDTO nl;

    @Value("${scheduler.cron.expression}")
    private String cronExpression;

    private CronParser parser;

    private Environment env;

    @Autowired
    public EmailService(Environment env) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        this.scheduler = threadPoolTaskScheduler;  // инициализация в конструкторе
        this.env = env;
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

    /*public void setDateNl(NewsletterDTO nl) {
        final String cronExpression = parseDateToCron(nl.getDate()); //todo здесь переделать дату формата dd.MM.yyyy HH:mm в cron и записать его в проперти this.cronExpression
        String propertyString = "scheduler.cron.expression";
        Properties properties = new Properties();
        properties.setProperty(propertyString, cronExpression);

        MutablePropertySources propertySources = ((org.springframework.core.env.AbstractEnvironment) env).getPropertySources();
        propertySources.addFirst(new PropertiesPropertySource("customProperty", properties));

        this.nl = nl;
        //this.dateNl = nl.getDate();
    }

    public static String parseDateToCron(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd"); //день в месяце
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

            String day = dayFormat.format(date);
            String month = monthFormat.format(date);
            String hour = hourFormat.format(date);
            String minute = minuteFormat.format(date);
            String year = yearFormat.format(date);

            return String.format("0 %s %s %s %s %s", minute, hour, day, month, year);
        } catch (ParseException e){
            e.printStackTrace();
            return "Неверный формат даты";
        }
    }

    @Scheduled(cron = "${scheduler.cron.expression}")
    public void messNewsletter(){
        List<User> users = userRepository.getAllUsers();   //далее все для отправки
        for (User u : users) {
            if (u.getRole() != Role.ADMIN) {
                Logger logger = LoggerFactory.getLogger(EmailService.class);
                Newsletter newsletter = nlMapper.newsletterDTOTonewsletter(nl);  //для установки статуса

                MimeMessage message = emailSender.createMimeMessage();
                try {
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                    helper.setText(nl.getText());
                    helper.setSubject(nl.getSubject());
                    helper.setTo(u.getEmail());
                    emailSender.send(message);
                    logger.info("письмо успешно отправлено к :" + u.getEmail()); //для проверки
                    newsletter.setStatus(true);       //todo запись статуса
                    synchronized (newsletter) {
                        newsletter.setMess(true);
                        nlRepository.save(newsletter);
                        this.template.convertAndSend("topic/newsletterSent", "sent");
                    }
                } catch (MessagingException exception) {
                    exception.printStackTrace();
                    logger.error("Не отправлено к ((:" + u.getEmail());
                    newsletter.setStatus(false);      //todo запись статуса
                    synchronized (newsletter) {
                        newsletter.setMess(false);
                        nlRepository.save(newsletter);
                        this.template.convertAndSend("topic/newsletterSent", "error");
                    }
                }
                nlRepository.save(newsletter);  //сохранение в бд
            }
        }
    }*/

    //отправка самого письма с датой
    //@SendTo("")//Возвращаемое значение транслируется всем подписчикам /topic/...
    public void messNewsletter(NewsletterDTO nl){
        Logger logger = LoggerFactory.getLogger(EmailService.class);
        Newsletter newsletter = nlMapper.newsletterDTOTonewsletter(nl);  //для установки статуса
        nlRepository.save(newsletter);  //первичная запись в бд сос статусом "в очереди"

        String dateString = nl.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); //создается формат
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);         //для времени

        List<User> users = userRepository.getAllUsers();   //далее все для отправки
        for (User u : users) {
            if (u.getRole() != Role.ADMIN) {
                //здесь должно быть создание и запись в репозиторий
                scheduler.schedule(() -> {              //кроме этого
                    MimeMessage message = emailSender.createMimeMessage();
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                        helper.setText(nl.getText());
                        helper.setSubject(nl.getSubject());
                        helper.setTo(u.getEmail());
                        emailSender.send(message); //отправка
                        logger.info("письмо успешно отправлено к :" + u.getEmail()); //для проверки
                        newsletter.setStatus(true);       //todo запись статуса
                        synchronized (newsletter){
                            newsletter.setMess(true);
                            nlRepository.save(newsletter);  //обновление с новым статусом
                            this.template.convertAndSend("topic/newsletterSent", "sent");
                        }
                    } catch (MessagingException exception) {
                        exception.printStackTrace();
                        logger.error("Не отправлено к ((:" + u.getEmail());
                        newsletter.setStatus(false);      //todo запись статуса
                        synchronized (newsletter){
                            newsletter.setMess(false);
                            nlRepository.save(newsletter);  //обновление с новым статусом
                            this.template.convertAndSend("topic/newsletterSent", "error");
                        }
                    }

                    //nlRepository.save(newsletter);  //обновление в бд
                }, java.util.Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
            }
        }
        //todo сделать через какое-то время перезаписать mess, когда отправляется
    }

    /*public void messNewsletter(NewsletterDTO nl){
        Logger logger = LoggerFactory.getLogger(EmailService.class);
        Newsletter newsletter = nlMapper.newsletterDTOTonewsletter(nl);  //для установки статуса
        nlRepository.save(newsletter);

        String dateString = nl.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); //создается формат
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);         //для времени

        List<User> users = userRepository.getAllUsers();   //далее все для отправки
        for (User u : users) {
            if (u.getRole() != Role.ADMIN) {
                MimeMessage message = emailSender.createMimeMessage();
                try { //здесь должно быть создание и запись в репозиторий
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                    helper.setText(nl.getText());
                    helper.setSubject(nl.getSubject());
                    helper.setTo(u.getEmail());


                    scheduler.schedule(() -> {              //кроме этого

                        emailSender.send(message); //отправка
                        logger.info("письмо успешно отправлено к :" + u.getEmail()); //для проверки
                        newsletter.setStatus(true);       //todo запись статуса
                        synchronized (newsletter) {
                            newsletter.setMess(true);
                            nlRepository.save(newsletter);
                            this.template.convertAndSend("topic/newsletterSent", "sent");
                        }
                    }, java.util.Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
                } catch (MessagingException exception) {
                    exception.printStackTrace();
                    logger.error("Не отправлено к ((:" + u.getEmail());
                    newsletter.setStatus(false);      //todo запись статуса
                    synchronized (newsletter){
                        newsletter.setMess(false);
                        nlRepository.save(newsletter);
                        this.template.convertAndSend("topic/newsletterSent", "error");
                    }
                }

                nlRepository.save(newsletter);  //сохранение в бд

            }
        }
        //todo сделать через какое-то время перезаписать mess, когда отправляется
    }*/

    /*@Scheduled(fixedDelay = 60000)
    public void messNewsletter(){
        String cronExpression = getCronExprConfig();
        if(thisTime()){                                //сравниваем время в проперти с нынешним и выполняем отправку, когда придет время
            List<User> users = userRepository.getAllUsers();   //далее все для отправки
            for (User u : users) {
                if (u.getRole() != Role.ADMIN) {
                    Logger logger = LoggerFactory.getLogger(EmailService.class);
                    Newsletter newsletter = nlMapper.newsletterDTOTonewsletter(nl);  //для установки статуса

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
                            this.template.convertAndSend("topic/newsletterSent", "sent");
                        }
                    } catch (MessagingException exception) {
                        exception.printStackTrace();
                        logger.error("Не отправлено к ((:" + u.getEmail());
                        newsletter.setStatus(false);      //todo запись статуса
                        synchronized (newsletter){
                            newsletter.setMess(false);
                            nlRepository.save(newsletter);
                            this.template.convertAndSend("topic/newsletterSent", "error");
                        }
                    }
                    nlRepository.save(newsletter);  //сохранение в бд
                }

            }
        }
    }

    private String getCronExprConfig(){
        this.parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        return cronExpression;
    }

    public boolean thisTime(){
        Cron cron = parser.parse(cronExpression);
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime now = ZonedDateTime.now();

        return executionTime.isMatch(now);
    }*/

    // удаление
    public Long deleteNl(Long id) {
        nlRepository.deleteById(id);
        return id;
    }

    // полностью переделать или частично
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
        //changingNl.setAddress(nl.getAddress());
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
        //changingNl.setAddress(nl.getAddress());
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






