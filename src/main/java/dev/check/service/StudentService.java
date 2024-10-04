package dev.check.service;

import dev.check.dto.ParamForGet;
import dev.check.dto.Student;
import dev.check.dto.StudentUpdate;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import dev.check.manager.ManagerUtils;
import dev.check.mapper.StudentMapper;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentMapper studentDtoMapper;

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    @Transactional
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

    @Transactional
    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public Page<Student> getStudents(ParamForGet request) {

        Pageable pageable = ManagerUtils.createPageable(request.getPage(),
                request.getSize(), request.getColumn(), request.getDirection());

        UsernamePasswordAuthenticationToken userData = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Page<StudentEntity> students;
        if (getRoles(userData)) {
            students = studentRepository.getStudentsStudent(request.getFilter(), pageable, userData.getName()); //без телефонов остальных
        } else {
            students = studentRepository.getStudentsAdmin(request.getFilter(), pageable); //полностью со всеми данными
        }
        return mapDtoForPage(students);
    }

    private Page<Student> mapDtoForPage(Page<StudentEntity> students) {
        List<Student> newsletterDTOs = studentDtoMapper.studentEntityListToStudentList(students.getContent());
        return new PageImpl<>(newsletterDTOs, students.getPageable(), students.getTotalElements());
    }

    private Boolean getRoles(UsernamePasswordAuthenticationToken userData) {
        List<Role> roles = userData.getAuthorities().stream()   // в поток
                .map(auth -> Role.valueOf(auth.getAuthority())) // для каждого элемента
                .collect(Collectors.toList());                  // в лист

        List<Role> allRoles = new ArrayList<>(Arrays.asList(Role.values()));
        return allRoles.contains(roles);
    }

}
