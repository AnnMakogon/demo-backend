package dev.check.service;

import dev.check.dto.StudentRegistr;
import dev.check.entity.PasswordEntity;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import dev.check.mapper.StudentMapper;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserRegService {

    private final StudentMapper studentDtoMapper;

    private final UserMapper userMapper;

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    @Transactional
    public StudentRegistr regStudent(StudentRegistr studentRegistr) {
        StudentEntity student = studentDtoMapper.studentDtoRegistrToStudent(studentRegistr); //здесь уже enum
        UserEntity user = new UserEntity(studentRegistr.getFio(),
                userMapper.stringToRole(studentRegistr.getRole()),
                new PasswordEntity(studentRegistr.getPasswordId()),
                studentRegistr.isEnable(), studentRegistr.getEmail());
        userRepository.save(user);
        studentRepository.save(student);

        return studentRegistr;
    }




}
