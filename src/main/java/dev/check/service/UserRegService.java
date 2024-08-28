package dev.check.service;

import dev.check.DTO.StudentRegistr;
import dev.check.entity.PasswordEntity;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import dev.check.mapper.StudentMapper;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public StudentRegistr regStudent(StudentRegistr studentRegistr) {
        StudentEntity student = studentDtoMapper.studentDtoRegistrToStudent(studentRegistr); //здесь уже enum
        UserEntity user = new UserEntity(studentRegistr.getFio(),
                userMapper.stringToRole(studentRegistr.getRole()),
                new PasswordEntity(studentRegistr.getPasswordId()),
                studentRegistr.isEnable(), studentRegistr.getEmail());
        // что такое транзакции, если после сохранения userRepository упадет, какие данные будут в бд, почему?- см в "ВЫУЧИТЬ ДЛЯ ЗАДАЧИ"
        userRepository.save(user);
        studentRepository.save(student);

        return studentRegistr;
    }


}
