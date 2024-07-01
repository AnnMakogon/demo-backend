package dev.check.service;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.controller.AuthorizationController;
import dev.check.controller.RegistrationController;
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
    @Autowired
    private EmailService emailService;

    public StudentRegistrDTO regStudent(StudentRegistrDTO studentRegistr) {
        if(Objects.equals(studentRegistr.getRole(), "")){
            studentRegistr.setRole("STUDENT");
        }
        Student student = studentDtoMapper.studentDtoAuthToStudent(studentRegistr);
        User user = new User(studentRegistr.getFio(),
                userMapper.stringToRole(studentRegistr.getRole()),
                new Password(studentRegistr.getPassword_id()),
                studentRegistr.isEnable(), studentRegistr.getEmail());
        userRepository.save(user);
        studentRepository.save(student);
        System.out.println("REEEEEEEEEEEEEEG UserRegService");

        return studentRegistr;
    }


}
