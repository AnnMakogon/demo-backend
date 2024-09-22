package dev.check.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.dto.StudentRegistr;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.manager.SentOnTime.SentManagerOnTime;
import dev.check.mapper.NewsletterMapper;
import dev.check.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsletterService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private NewsletterRepository newsletterRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsletterMapper newsletterMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AddressDepartmentRepository addressDepartmentRepository;

    @Autowired
    private AddressGroupRepository addressGroupRepository;

    private SentManagerOnTime sentManagerOnTime;

    @Value("${emailService.registr.url}")
    private String url;

    @Value("${emailService.registr.textHolder}")
    private String textHolder;

    @Value("${emailService.registr.subject}")
    private String subject;

    @Value("${emailService.registr.text}")
    private String text;

    //это для подтверждения регистрации
    @Transactional
    public void sendSimpleMessage(StudentRegistr newStudent) {
        if (userRepository.findUserByName(newStudent.getFio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exist");
        }
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
        } catch (JsonProcessingException | UnsupportedEncodingException |
                 MessagingException exception) {
            exception.printStackTrace();
        }
    }

    //создание
    @Transactional
    public List<Long> createNewsletter(Newsletter newsletter0) {
        newsletter0.setStatus("INPROCESSING");
        List<Long> idList = new ArrayList<>();
        List<NewsletterEntity> newsletters = mapToNewsletters(newsletter0);
        newsletters.forEach((NewsletterEntity newsletter) -> {
            newsletter.setStatus(Status.NOTSENT);
            newsletter.setDate(newsletter.getDate().minusHours(3));
            newsletterRepository.save(newsletter);  //первичная запись в бд со статусом "в очереди"
            idList.add(newsletter.getId());
            sentManagerOnTime.checkQueue(newsletter);
        });
        return idList;

    }

    private List<NewsletterEntity> mapToNewsletters(Newsletter newsletter) {
        List<NewsletterEntity> newsletters = new ArrayList<>();

        for (Address address : newsletter.getAddress()) {
            if ("ADMIN".equalsIgnoreCase(address.getRole()) && (address.getGroups() == null || address.getGroups().isEmpty())) {
                addAdminNewsletter(newsletters, newsletter);
            } else {
                addGroupNewsletters(newsletters, newsletter, address);
            }
        }
        return newsletters;
    }

    private void addAdminNewsletter(List<NewsletterEntity> newsletters, Newsletter newsletter) {
        NewsletterEntity newsletterEntity = newsletterMapper.newsletterToNewsletterEntity(newsletter);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setRole(Role.ADMIN);
        addressEntity.setNewsletter(newsletterEntity);

        newsletterEntity.setAddresses(new ArrayList<>());
        newsletterEntity.getAddresses().add(addressEntity);

        newsletters.add(newsletterEntity);
    }

    private void addGroupNewsletters(List<NewsletterEntity> newsletters, Newsletter newsletter, Address address) {
        List<NewsletterEntity> entities = address.getGroups().stream()
                .map(group -> {
                    NewsletterEntity newsletterEntity = newsletterMapper.newsletterToNewsletterEntity(newsletter);

                    AddressEntity addressEntity = newsletterMapper.addressToAddressEntity(address);

                    addressEntity.setNewsletter(newsletterEntity);

                    newsletterEntity.setAddresses(new ArrayList<>());
                    newsletterEntity.getAddresses().add(addressEntity);

                    return newsletterEntity;
                })
                .collect(Collectors.toList());

        newsletters.addAll(entities);
    }

    @Transactional
    public Long deleteNewsletter(Long id) {
        newsletterRepository.deleteById(id);
        sentManagerOnTime.checkQueueDelete(id);
        return id;
    }

    @Transactional
    public Newsletter changeNewsletter(Newsletter newsletterDto) {
        if (newsletterDto.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        NewsletterEntity newsletterEntity = newsletterRepository.findById(newsletterDto.getId()).get();
        NewsletterEntity saveNewsletter = newsletterMapper.newsletterDtoToNewsletter(newsletterDto);

        if (newsletterEntity.getAddresses().isEmpty()) {
            newsletterEntity.getAddresses().clear();
        }
        saveNewsletter.setAddresses(newsletterEntity.getAddresses());
        saveNewsletter.setDate(saveNewsletter.getDate().minusHours(3));
        newsletterRepository.save(saveNewsletter);
        sentManagerOnTime.checkQueue(saveNewsletter);
        return newsletterDto;
    }

    @Transactional
    public Newsletter changeDateNewsletter(Newsletter newsletterDto) {
        if (newsletterDto == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        NewsletterEntity newsletter = newsletterMapper.newsletterDtoToNewsletter(newsletterDto);
        NewsletterEntity changingNewsletter = newsletterRepository.findById(newsletter.getId()).orElseThrow(() ->
                new RuntimeException("newsletter with id: " + newsletter.getId() + "was not found"));
        changingNewsletter.setDate(newsletter.getDate());
        changingNewsletter.setDate(changingNewsletter.getDate().minusHours(3));
        newsletterRepository.save(changingNewsletter);
        sentManagerOnTime.checkQueue(changingNewsletter);
        return newsletterDto;
    }

    @Transactional
    public Page<Newsletter> getNewsletter(String filter, Pageable pageable, boolean showflag) {
        Page<NewsletterEntity> newsletters;
        if (showflag) {   // если передалось тру - показываем только непрочитанные
            newsletters = newsletterRepository.getUnreadNewsletter(filter, pageable);
        } else {
            newsletters = newsletterRepository.getNewsletter(filter, pageable);
        }
        return mappToPage(newsletters);
    }

    private Page<Newsletter> mappToPage(Page<NewsletterEntity> newsletters) {
        List<Newsletter> newsletterDTOs = newsletterMapper.newsletterEntityListToNewsletterList(newsletters.getContent());
        return new PageImpl<>(newsletterDTOs, newsletters.getPageable(), newsletters.getTotalElements());
    }

    @Transactional
    public List<Newsletter> getForSentScheduler(int size) {
        return newsletterMapper.newsletterEntityListToNewsletterList( newsletterRepository.getForSentScheduler(size));
    }

    @Transactional
    public List<String> getEmailForSent(String course, String department, List<AddressGroupEntity> groups) {
        List<String> emails = null;
        for( AddressGroupEntity addressGroup : groups) {
            emails.addAll(userRepository.getNewsletterForSent(course, department, addressGroup.getGroup().getGroupValue().getGroup()));
        }
        return emails;
    }

    @Transactional
    public void save(NewsletterEntity newsletter) {
        newsletterRepository.save(newsletter);
    }

    @Transactional
    public void changeStatus(Long id, Status status) {
        newsletterRepository.changeStatus(id, status.toString());
    }

    @Transactional
    public NewsletterEntity findById(Long id) {
        return newsletterRepository.findById(id).get();
    }

    /*@Transactional(readOnly = true)
    public NewsletterEntity findNewsletterWithAddressesById(Long id){
        NewsletterEntity newsletter = newsletterRepository.findById(id).get();//.findNewsletterWithAddressesById(id);
        List<AddressEntity> addresses = addressRepository.findAllById(Collections.singleton(id));
        addresses.forEach((address -> {
            address.setCourse(courseRepository.findById(id).get());
        }) );
        newsletter.setAddresses(addresses);
        return newsletter;

    }*/

    @Transactional(readOnly = true)
    public NewsletterEntity findNewsletterWithAddressesById(Long newsletterId) {
        NewsletterEntity newsletter = newsletterRepository.findById(newsletterId).get();

            newsletter.getAddresses().forEach(address -> {
                address.getGroups().forEach(group -> {
                    addressGroupRepository.findById(group.getId()).orElse(null);
                });
                address.getDepartments().forEach(department -> {
                    addressDepartmentRepository.findById(department.getId()).orElse(null);
                });
            });

        return newsletter;
    }

}






