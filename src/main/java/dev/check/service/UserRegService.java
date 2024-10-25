package dev.check.service;

import dev.check.dto.StudentRegistr;
import dev.check.dto.User;
import dev.check.entity.*;
import dev.check.entity.EnumEntity.CourseNumber;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.GroupNumber;
import dev.check.mapper.StudentMapper;
import dev.check.mapper.UserMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserRegService {

    private final UserMapper userMapper;

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    @Transactional
    public StudentRegistr regStudent(StudentRegistr studentRegistr) {
        if (userRepository.findUserByName(studentRegistr.getFio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exist");
        }
        StudentEntity student = new StudentEntity(null, studentRegistr.getFio(),
                                new GroupEntity(GroupNumber.fromString(studentRegistr.getGroup())),
                                studentRegistr.getPhoneNumber(), new CourseEntity(CourseNumber.fromString(studentRegistr.getCourse())),
                                new DepartmentEntity(DepartmentName.valueOf(studentRegistr.getDepartmentName())), null);
        UserEntity user = new UserEntity(studentRegistr.getFio(),
                userMapper.stringToRole(studentRegistr.getRole()),
                new PasswordEntity(studentRegistr.getPasswordId()),
                true, studentRegistr.getEmail(), false);
        user.setStudent(student);
        student.setUser(user);
        userRepository.save(user);
        studentRepository.save(student);

        return studentRegistr;
    }

    @Transactional
    public User confirmation(Long id){
        userRepository.confirmation(id);
        return userMapper.userEntityToUserDto( userRepository.findById(id).get());
    }
}
