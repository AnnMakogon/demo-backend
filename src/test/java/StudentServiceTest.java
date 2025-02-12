import dev.check.dto.ParamForGet;
import dev.check.dto.Student;
import dev.check.dto.StudentUpdate;
import dev.check.entity.CourseEntity;
import dev.check.entity.DepartmentEntity;
import dev.check.entity.EnumEntity.CourseNumber;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.GroupNumber;
import dev.check.entity.GroupEntity;
import dev.check.entity.StudentEntity;
import dev.check.manager.ManagerUtils;
import dev.check.mapper.StudentMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import dev.check.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateStudentWithValidStudent(){
        StudentUpdate student = new StudentUpdate(93L, "Алла Борисовна", "1.3", "+89100",
                "KFA", "1");
        StudentEntity studentEntity = new StudentEntity(93L, "Алла Борисовна", new GroupEntity(GroupNumber.GROUP_3_1), "+89100",
                new CourseEntity(CourseNumber.COURSE_1), new DepartmentEntity(DepartmentName.KFA), null);

        when(studentMapper.studentDtoToStudent(student)).thenReturn(studentEntity);
        when(studentRepository.save(studentEntity)).thenReturn(studentEntity);

        StudentUpdate updateStudent = studentService.updateStudent(student);

        assertEquals(student, updateStudent);
        verify(studentRepository).save(studentEntity);
    }

    @Test
    void updateStudentWithoutId() {

        StudentUpdate student = new StudentUpdate(null, "Алла Борисовна", "1.3", "+89100",
                "KFA", "1");

        assertThrows(RuntimeException.class, () -> studentService.updateStudent(student));
    }

    @Test
    void removeStudentWithWalidStudent() {
        Long id = 1L;
        when(studentRepository.existsById(id)).thenReturn(true);
        studentService.removeStudent(id);
        verify(studentRepository).deleteById(id);
    }

    @Test
    void getStudents() {
        ParamForGet request = new ParamForGet(0, 10, "fio", "", "", false);
        Pageable pageable = ManagerUtils.createPageable(request.getPage(), request.getSize(), request.getColumn(), request.getDirection());
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity(92L, "Алла Борисовна", new GroupEntity(GroupNumber.GROUP_3_1), "+89100",
                new CourseEntity(CourseNumber.COURSE_1), new DepartmentEntity(DepartmentName.KFA), null));
        students.add(new StudentEntity(93L, "Петров Иван Николаевич", new GroupEntity(GroupNumber.GROUP_3_1), "+89100",
                new CourseEntity(CourseNumber.COURSE_1), new DepartmentEntity(DepartmentName.KFA), null));

        when(studentRepository.getStudentsStudent(request.getFilter(), pageable, "Алла Борисовна")).thenReturn(new PageImpl<>(students, pageable, 2));
        when(studentMapper.studentEntityListToStudentList(students)).thenReturn(new ArrayList<>());
        User user = new User("Ann", "PROTECTED", true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("STUDENT")));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("Credentials: " + auth.getCredentials());

        Page<Student> studentsPage = studentService.getStudents(request);

        assertEquals(2, studentsPage.getTotalElements());
        assertNotNull(studentsPage);
    }
}
