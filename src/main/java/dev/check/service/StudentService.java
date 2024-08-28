package dev.check.service;

import dev.check.DTO.StudentFullTable;
import dev.check.DTO.StudentUpdate;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import dev.check.mapper.StudentMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentDtoMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public StudentUpdate updateStudent(StudentUpdate studentDto) {
        if (studentDto.getId() == null) {
            throw new RuntimeException("id of changing student cannot be null");
        }
        StudentEntity student = studentDtoMapper.studentDtoToStudent(studentDto);

        studentRepository.save(student);  // обновление в базе студентов

        //далее все для проверки того, меняется ли вошедший юзер
        UsernamePasswordAuthenticationToken userData = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        List<UserEntity> users = userRepository.getAllUsers();
        String username = "";  // в базе
        for (UserEntity u : users) {
            if (Objects.equals(u.getId(), student.getId())) { //проверка: меняем ли юзера
                if (Objects.equals(u.getUsername(), userData.getName())) {  //
                    username = u.getUsername();
                }
                UserEntity changingUser = userRepository.findById(student.getId()).orElseThrow(() ->
                        new RuntimeException("student with id: " + student.getId() + "was not found"));
                changingUser.setUsername(studentDto.getFio());
                if (Objects.equals(username, userData.getName())) { // проверка и перезапись: изменяет ли вошедший сам себя(сравниваем userData.getName и changingUser.getFio )
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

    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    //get всех студентов и проверка через contains какая роль у вошедшего человека
    public Page<StudentFullTable> getStudents(String substring, Pageable pageable) {

        UsernamePasswordAuthenticationToken userData = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Page<StudentEntity> students;
        if (getRoles(userData)) {
            students = studentRepository.getStudentsStudent(substring, pageable, userData.getName()); //без телефонов остальных
        } else {
            students = studentRepository.getStudentsAdmin(substring, pageable); //полностью со всеми данными
        }
        return convertListToPage( studentDtoMapper.studentEntityListToStudentDtoFull(students.toList()), pageable);
    }

    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<T> subList = list.subList(start, end);

        return new PageImpl<>(subList, pageable, list.size());
    }

    private Boolean getRoles(UsernamePasswordAuthenticationToken userData){
        List<Role> roles = userData.getAuthorities().stream()   // в поток
                .map(auth -> Role.valueOf(auth.getAuthority())) // для каждого элемента
                .collect(Collectors.toList());                  // в лист

        List<Role> allRoles = new ArrayList<>(Arrays.asList(Role.values()));
        return allRoles.contains(roles);
    }

}
