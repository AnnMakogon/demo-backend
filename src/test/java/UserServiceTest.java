import dev.check.dto.User;
import dev.check.repositories.UserRepository;
import dev.check.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;  //заглушка

    @InjectMocks
    private UserService userService; // внедрение зависимостей: позволяет использовать методы из оригинала, но с заглушками

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this); // инициализация моков - заглушек
    }

    @Test
    public void testGetUserForLogin(){
        String username = "student 0";
        Long userId = 93L;
        Boolean emailEnable = true;
        Long studentId = 92L;

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority("STUDENT")));

        when(userRepository.findIdByName(username)).thenReturn(userId);
        when(userRepository.findEnableEmailByName(username)).thenReturn(emailEnable);
        when(userRepository.findStudentIdByName(username)).thenReturn(studentId);

        User result = userService.getUserForLogin(token);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals("[STUDENT]", result.getRole());
        assertTrue(result.isEnable());
        assertTrue(result.isEnableEmail());
        assertEquals(studentId, result.getStudentId());

        verify(userRepository).findIdByName(username);   // проверка, что метод из репозитория были вызваны правильно и с правильными аргументами
        verify(userRepository).findEnableEmailByName(username);
        verify(userRepository).findStudentIdByName(username);
    }
}
