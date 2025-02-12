import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.dto.User;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
import dev.check.entity.UserEntity;
import dev.check.mapper.NewsletterMapper;
import dev.check.repositories.*;
import dev.check.service.NewsletterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NewsletterServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private NewsletterRepository newsletterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NewsletterMapper newsletterMapper;

    @Mock
    private AddressDepartmentRepository addressDepartmentRepository;

    @Mock
    private AddressGroupRepository addressGroupRepository;

    @Mock
    private AddressCourseRepository addressCourseRepository;

    @InjectMocks
    private NewsletterService newsletterService;

    private User testUser;
    private Newsletter testNewsletter;
    private NewsletterEntity testNewsletterEntity;

    private String url = "http://localhost:8080/confirm/";

    private String textHolder = "Click here to confirm your registration";

    private String subject = "Registration Confirmation";

    private String text = "Thank you for registering!";
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(100L, "Admin", "[ADMIN]", true, true, null);

        testNewsletter = new Newsletter(104L, OffsetDateTime.now(), "Test text", "Test subject", new ArrayList<>(Collections.singletonList(new Address("[STUDENT]", Collections.singletonList("1"), Collections.singletonList("KFA"), Collections.singletonList("1.3")))), false, "NOTSENT");

        testNewsletterEntity = new NewsletterEntity(104L, OffsetDateTime.now(), "Test text", "Test subject", null, false, Status.NOTSENT);

        newsletterService.setUrl(url);
        newsletterService.setTextHolder(textHolder);
        newsletterService.setSubject(subject);
        newsletterService.setText(text);
    }

    @Test
    public void sendRegistrMessageTest() throws MessagingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(99L);
        userEntity.setEmail("email@ru");

        when(userRepository.getUserByName(testUser.getUsername())).thenReturn(userEntity);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        doNothing().when(emailSender).send(mimeMessage);

        newsletterService.sendRegistrMessage(testUser);

        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void createNewsletterTest() {
        when(newsletterMapper.newsletterToNewsletterEntity(any(Newsletter.class))).thenReturn(testNewsletterEntity);
        when(newsletterRepository.save(any(NewsletterEntity.class))).thenReturn(testNewsletterEntity);

        List<Long> result = newsletterService.createNewsletter(testNewsletter);

        verify(newsletterRepository, times(1)).save(any(NewsletterEntity.class));
        assertEquals(1, result.size());
        assertEquals(testNewsletterEntity.getId(), result.get(0));
    }

    @Test
    public void deleteNewsletterTest() {
        Long result = newsletterService.deleteNewsletter(1L);
        verify(newsletterRepository, times(1)).deleteById(1L);
        assertEquals(1L, result);
    }

    @Test
    public void testChangeNewsletter() {
        when(newsletterRepository.findById(anyLong())).thenReturn(Optional.of(testNewsletterEntity));
        when(newsletterRepository.findById(anyLong())).thenReturn(Optional.of(testNewsletterEntity));
        when(newsletterMapper.newsletterDtoToNewsletter(any(Newsletter.class))).thenReturn(testNewsletterEntity);

        Newsletter result = newsletterService.changeNewsletter(testNewsletter);

        verify(newsletterRepository, times(1)).findById(anyLong());
        verify(newsletterRepository, times(1)).save(any(NewsletterEntity.class));
        assertEquals(testNewsletter.getId(), result.getId());
    }

}
