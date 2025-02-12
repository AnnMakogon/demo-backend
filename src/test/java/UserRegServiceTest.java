import dev.check.dto.StudentRegistr;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import dev.check.service.UserRegService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserRegServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegService userRegService;

    private StudentRegistr studentRegistr;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentRegistr = new StudentRegistr(93L, "student 0" , "1.3", "+89100",
                "1111", "[STUDENT]", true, null, "KFA", "1");
    }

    @Test
    void testRegStudent() {

        when(userRepository.findUserByName(studentRegistr.getFio())).thenReturn(false);
        when(userMapper.stringToRole(studentRegistr.getRole())).thenReturn(Role.STUDENT);

        userRegService.regStudent(studentRegistr);

        verify(userRepository).save(any(UserEntity.class));
        verify(studentRepository).save(any(StudentEntity.class));
    }

    @Test
    void testRegStudentWhenUsernameAlreadyExists() {
        when(userRepository.findUserByName(studentRegistr.getFio())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userRegService.regStudent(studentRegistr);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("username already exist", exception.getReason());

        verify(userRepository, never()).save(any(UserEntity.class));
        verify(studentRepository, never()).save(any(StudentEntity.class));
    }
}
