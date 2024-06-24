package dev.check.service;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.entity.Password;
import dev.check.entity.Student;
import dev.check.entity.User;
import dev.check.mapper.StudentMapper;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserRegService {

    @Autowired
    private StudentMapper studentDtoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    public StudentRegistrDTO regStudent(StudentRegistrDTO studentDtoAuth) {
        if(Objects.equals(studentDtoAuth.getRole(), "")){
            studentDtoAuth.setRole("STUDENT");
        }
        Student student = studentDtoMapper.studentDtoAuthToStudent(studentDtoAuth);
        User user = new User(studentDtoAuth.getFio(),
                            userMapper.stringToRole(studentDtoAuth.getRole()),
                            new Password(studentDtoAuth.getPassword_id()),
                            studentDtoAuth.isEnable());
        userRepository.save(user);
        studentRepository.save(student);
        return studentDtoAuth;
    }


}
