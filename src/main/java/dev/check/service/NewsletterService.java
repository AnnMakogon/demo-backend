package dev.check.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.check.dto.*;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.entity.UserEntity;
import dev.check.manager.ManagerUtils;
import dev.check.mapper.NewsletterMapper;

import dev.check.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsletterService {

    private final JavaMailSender emailSender;

    private final NewsletterRepository newsletterRepository;

    private final UserRepository userRepository;

    private final NewsletterMapper newsletterMapper;

    private final AddressDepartmentRepository addressDepartmentRepository;

    private final AddressGroupRepository addressGroupRepository;

    private final AddressCourseRepository addressCourseRepository;

    @Value("${emailService.registr.url}")
    private String url;

    @Value("${emailService.registr.textHolder}")
    private String textHolder;

    @Value("${emailService.registr.subject}")
    private String subject;

    @Value("${emailService.registr.text}")
    private String text;

    //для подтверждения регистрации
    @Transactional
    public void sendRegistrMessage(User newStudent) {
        try {
            UserEntity userWithEmail = userRepository.getUserByName(newStudent.getUsername());

            String urlWithId = this.url+userWithEmail.getId();

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(userWithEmail.getEmail());
            helper.setSubject(this.subject);

            String link = "<a href='" + urlWithId + "'>" + this.textHolder + "</a>";
            String fullText = "<html><body>" +
                    "<p>" + this.text + "</p>" +
                    "<p>" + link + "</p>" +
                    "</body></html>";
            helper.setText(fullText, true);
            emailSender.send(message);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }

    //создание
    @Transactional
    public List<Long> createNewsletter(Newsletter newsletter) {
        newsletter.setStatus("INPROCESSING");
        List<Long> idList = new ArrayList<>();
        List<NewsletterEntity> newsletters = mapToNewsletters(newsletter);
        newsletters.forEach((NewsletterEntity newsletter0) -> {
            newsletter0.setStatus(Status.NOTSENT);
            newsletter0.setDate(newsletter0.getDate().minusHours(3));
            newsletterRepository.save(newsletter0);  //первичная запись в бд со статусом "в очереди"
            idList.add(newsletter0.getId());
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
        return id;
    }

    @Transactional
    public Newsletter changeNewsletter(Newsletter newsletter) {
        if (newsletter.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        NewsletterEntity newsletterEntity = newsletterRepository.findById(newsletter.getId()).get();
        NewsletterEntity saveNewsletter = newsletterMapper.newsletterDtoToNewsletter(newsletter);

        if (newsletterEntity.getAddresses().isEmpty()) {
            newsletterEntity.getAddresses().clear();
        }
        saveNewsletter.setAddresses(newsletterEntity.getAddresses());
        saveNewsletter.setDate(saveNewsletter.getDate().minusHours(3));
        newsletterRepository.save(saveNewsletter);
        return newsletter;
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
        return newsletterDto;
    }
    @Transactional
    public Page<Newsletter> getNewsletter(ParamForGet request) {
        Page<NewsletterEntity> newsletters;
        Pageable pageable = ManagerUtils.createPageable(
                request.getPage(), request.getSize(), request.getColumn(), request.getDirection());
        if (request.isShowFlag()) {   // если передалось тру - показываем только непрочитанные
            newsletters = newsletterRepository.getNotSentNewsletter(request.getFilter(), pageable);
        } else {
            newsletters = newsletterRepository.getNewsletter(request.getFilter(), pageable);
        }
        return mapDtoForPage(newsletters);
    }

    private Page<Newsletter> mapDtoForPage(Page<NewsletterEntity> newsletters) {
        List<Newsletter> newsletterDTOs = newsletterMapper.newsletterEntityListToNewsletterList(newsletters.getContent());
        return new PageImpl<>(newsletterDTOs, newsletters.getPageable(), newsletters.getTotalElements());
    }

    @Transactional
    public List<Newsletter> getForSentScheduler(int size) {
        List<NewsletterEntity> newsletterEntities =  newsletterRepository.getForSentScheduler(size);
        return newsletterMapper.newsletterEntityListToNewsletterList(newsletterEntities);
    }

    @Transactional
    public List<String> getEmailForSent(String course, List<AddressDepartmentEntity> departments, List<AddressGroupEntity> groups) {
        return groups.stream()
                .flatMap(addressGroup -> departments.stream()
                        .flatMap(addressDepartment ->
                                userRepository.getNewsletterForSent(
                                        course, addressDepartment.getDepartment().getDepartmentName().toString(), addressGroup.getGroup().getGroupValue().toString()
                                ).stream()
                        )
                ).collect(Collectors.toList());
    }

    @Transactional
    public void changeStatus(Long id, Status status) {
        newsletterRepository.changeStatus(id, status.toString());
        if (status == Status.SUCCESSFULLY){
            newsletterRepository.changeSent();
        }
    }

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
                address.getCourses().forEach(course -> {
                    addressCourseRepository.findById(course.getId()).orElse(null);
                });
            });

        return newsletter;
    }

    @Transactional
    public void setAllStatusNotSent(){
        newsletterRepository.setAllStatusNotSent();
    }

}






