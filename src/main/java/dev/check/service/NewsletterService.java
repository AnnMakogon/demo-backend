package dev.check.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.check.DTO.Address;
import dev.check.DTO.Newsletter;
import dev.check.DTO.StudentRegistr;
import dev.check.entity.AddressEntity;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.manager.SentManager;
import dev.check.mapper.NlMapper;
import dev.check.repositories.NewsletterRepository;
import dev.check.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@PropertySource("classpath:email.properties")
public class NewsletterService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NewsletterRepository nlRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NlMapper nlMapper;

    @Value("${emailService.registr.url}")
    private String url;

    @Value("${emailService.registr.textHolder}")
    private String textHolder;

    @Value("${emailService.registr.subject}")
    private String subject;

    @Value("${emailService.registr.text}")
    private String text;

    //это для регистрации подтверждение
    public void sendSimpleMessage(StudentRegistr newStudent) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStudentRegistr = objectMapper.writeValueAsString(newStudent);
            String encodedJsonStudentRegistr = URLEncoder.encode(jsonStudentRegistr, StandardCharsets.UTF_8.toString());

            String urlWithJson = this.url + "?data=" + encodedJsonStudentRegistr;

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(newStudent.getEmail());
            helper.setSubject(this.subject);

            String link = "<a href='" + urlWithJson + "'>" + this.textHolder + "</a>";
            String fullText = "<html><body>" +
                              "<p>" + this.text + "</p>" +
                              "<p>" + link + "</p>" +
                              "</body></html>";
            helper.setText(fullText, true);
            emailSender.send(message);
        } catch (MessagingException | JsonProcessingException | UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
    }

    //создание
    @Transactional
    public void createNewsletter(Newsletter nl) {
        nl.setStatus("INPROCESSING");
        List<NewsletterEntity> newsletters = mapToNewsletters(nl);
        newsletters.forEach((NewsletterEntity newsletter) -> {
            /*try {
                sentManager.sentNewsletter(newsletter);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }*/
            stopClean();
            newsletter.setStatus(Status.SUCCESSFULLY);
            nlRepository.save(newsletter);  //первичная запись в бд со статусом "в очереди"
            filling();

        });
    }

    private List<NewsletterEntity> mapToNewsletters(Newsletter newsletter) {
        List<NewsletterEntity> newsletters = new ArrayList<>();

        for (Address address : newsletter.getAddress()) {
            if ("ADMIN".equalsIgnoreCase(address.getRole()) && (address.getGroup() == null || address.getGroup().isEmpty())) {
                addAdminNewsletter(newsletters, newsletter);
            } else {
                addGroupNewsletters(newsletters, newsletter, address);
            }
        }
        return newsletters;
    }

    private void addAdminNewsletter(List<NewsletterEntity> newsletters, Newsletter newsletter) {
        NewsletterEntity newsletterEntity = nlMapper.newsletterToNewsletterEntity(newsletter);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setRole(Role.ADMIN);
        addressEntity.setNewsletter(newsletterEntity);

        newsletterEntity.setAddresses(new ArrayList<>());
        newsletterEntity.getAddresses().add(addressEntity);

        newsletters.add(newsletterEntity);
    }

    private void addGroupNewsletters(List<NewsletterEntity> newsletters, Newsletter newsletter, Address address) {
        List<NewsletterEntity> entities = address.getGroup().stream()
                .map(group -> {
                    NewsletterEntity newsletterEntity = nlMapper.newsletterToNewsletterEntity(newsletter);

                    AddressEntity addressEntity = nlMapper.addressToAddressEntity(address, group);

                    addressEntity.setNewsletter(newsletterEntity);

                    newsletterEntity.setAddresses(new ArrayList<>());
                    newsletterEntity.getAddresses().add(addressEntity);

                    return newsletterEntity;
                })
                .collect(Collectors.toList());

        newsletters.addAll(entities);
    }

    // удаление
    @Transactional
    public Long deleteNl(Long id) {
        nlRepository.deleteById(id);
        return id;
    }

    // полностью переделать
    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Newsletter update(Newsletter nlDto) {
        if (nlDto.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        try {

            stopClean();
            NewsletterEntity saveNl = nlMapper.newsletterDtoToNewsletter(nlDto);
            nlRepository.save(saveNl);  // обновление в базе
            filling();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return nlDto;
    }

    //передатировать
    //@Transactional
    public Newsletter dateUpdate(Newsletter nlDto) {
        if (nlDto == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        try {
            stopClean();

            NewsletterEntity nl = nlMapper.newsletterDtoToNewsletter(nlDto);
            NewsletterEntity changingNl = nlRepository.findById(nl.getId()).orElseThrow(() ->
                    new RuntimeException("newsletter with id: " + nl.getId() + "was not found"));
            changingNl.setDate(nl.getDate());
            nlRepository.save(changingNl);
            filling();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return nlDto;
    }

    //получение всего списка
    //@Transactional
    public Page<Newsletter> getNl(String filter, Pageable pageable, boolean showflag) {
        Page<NewsletterEntity> newsletters;
        if (showflag) {   // если передалось тру - показываем только непрочитанные
            newsletters = nlRepository.getUnreadNl(filter, pageable);
        } else {
            newsletters = nlRepository.getNewsletter(filter, pageable);
        }
        return mappToPage(newsletters);
    }

    private Page<Newsletter> mappToPage(Page<NewsletterEntity> newsletters) {
        List<Newsletter> newsletterDTOs = nlMapper.newsletterEntityListToNewsletterList(newsletters.getContent());
        return new PageImpl<>(newsletterDTOs, newsletters.getPageable(), newsletters.getTotalElements());
    }

    public List<NewsletterEntity> getForSentScheduler(){
        return nlRepository.getForSentScheduler();
    }

    public List<String> getNlForSent(Integer course, String department, Float group){
        return userRepository.getNlForSent(course, department, group);
    }

    public void save(NewsletterEntity newsletter){
        nlRepository.save(newsletter);
    }

    public void stopClean(){
        SentManager.newsletters = null;
    }

    public void filling(){
        SentManager.newsletters = getForSentScheduler();
    }

}






