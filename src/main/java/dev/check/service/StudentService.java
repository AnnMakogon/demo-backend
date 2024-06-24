package dev.check.service;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.DTO.StudentTableDTO;
import dev.check.DTO.StudentFullTableDTO;
import dev.check.DTO.StudentUpdateDTO;
import dev.check.IAuthenticationFacade;
import dev.check.entity.Password;
import dev.check.entity.Role;
import dev.check.entity.Student;
import dev.check.entity.User;
import dev.check.mapper.StudentMapper;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {
    /*
    сделать crud в сервисе, принимать он должен dto, конвертировать в entity через mapstruct,
    возвращать dto (в dto должны быть только нужные на фронте вещи, например, хэш пароля в списке возвращать не надо)
    что такое Component пересмотри еще раз
    что такое Repository, как инжектится
    что такое Скоупы бинов спринга, какой дефолтный?

    */

    @Autowired
    private StudentMapper studentDtoMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  StudentRepository studentRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public List<Student> getStudentsList() {
        List<Student> list = StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return list;
    }
    public StudentRegistrDTO addStudent(StudentRegistrDTO studentDtoAuth) {
        if(studentDtoAuth.getRole() == ""){
            studentDtoAuth.setRole("STUDENT");
        }
        Student student = studentDtoMapper.studentDtoAuthToStudent(studentDtoAuth);
        User user = userMapper.studentDtoAuthToUser(studentDtoAuth);
        userRepository.save(user);
        studentRepository.save(student);
        return studentDtoAuth;
    }

    public StudentUpdateDTO updateStudent(StudentUpdateDTO studentDto) {
        if(studentDto.getId() == null){
            throw new RuntimeException("id of changing student cannot be null");
        }
        Student student = studentDtoMapper.studentDtoToStudent(studentDto);

        Student changingStudent = StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()).stream()
                .filter(el -> Objects.equals(el.getId(), student.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("student with id: " + student.getId() + "was not found"));

        changingStudent.setId(studentDto.getId());
        changingStudent.setFio(studentDto.getFio());
        changingStudent.setGroup(studentDto.getGroup());
        changingStudent.setPhoneNumber(student.getPhoneNumber());

        studentRepository.save(changingStudent);

        UsernamePasswordAuthenticationToken userData = authenticationFacade.getAuthentication();

        User changingUser = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()).stream()
                .filter(el -> Objects.equals(el.getId(), student.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("student with id: " + student.getId() + "was not found"));

        List<User> users = userRepository.getAllUsers();
        String username = "";
        for(User u : users) {
            if (Objects.equals(u.getId(), student.getId())) {
                if(Objects.equals(u.getUsername(), userData.getName())){
                    username = u.getUsername();
                }
                changingUser.setId(studentDto.getId());
                changingUser.setUsername(studentDto.getFio());

                changingUser.setRole(Role.valueOf(userData.getAuthorities().toString().substring(1, userData.getAuthorities().toString().length() - 1)));
                changingUser.setPassword(new Password(""));
                changingUser.setEnable(true);

                if(Objects.equals(username, userData.getName())){
                    Authentication thisAuth = SecurityContextHolder.getContext().getAuthentication();
                    Authentication newUser = new UsernamePasswordAuthenticationToken(
                            changingUser.getUsername(),
                            thisAuth.getCredentials(),
                            thisAuth.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(newUser);
                }
                userRepository.save(changingUser);
            }
        }

        return studentDto;
    }

    public Long removeStudent(Long id) {
        studentRepository.deleteById(id);
        return id;
    }

    public List<StudentFullTableDTO> getStudents(String filter, Pageable pageable, String userName, String userRole ) {

        List<Student> students = new ArrayList<>();
        if(Objects.equals(userRole, "[STUDENT]")){
            students = studentRepository.getStudentsStudent(filter, pageable, userName);
        } else {
            students = studentRepository.getStudentsAdmin(filter, pageable);
        }
        List<StudentFullTableDTO> studentsDto = studentDtoMapper.studentListToStudentDtoFullList(students);

        return new ArrayList<>(studentsDto);
    }

}
