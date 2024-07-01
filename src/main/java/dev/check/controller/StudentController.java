package dev.check.controller;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.FindError500;

import dev.check.DTO.StudentFullTableDTO;
import dev.check.DTO.StudentUpdateDTO;
import dev.check.IAuthenticationFacade;
import dev.check.repositories.StudentRepository;
import dev.check.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@FindError500
@RequestMapping("/api/base")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentUpdateDTO changeStudent(@RequestBody StudentUpdateDTO changingStudent) {
        return studentService.updateStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        return studentService.removeStudent(id);
    }

    @GetMapping (value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentFullTableDTO> getStudentsPagSortFilter(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "size") int size,
                                                              @RequestParam(name = "column") String column,
                                                              @RequestParam(name = "direction") String direction,
                                                              @RequestParam(name = "filter") String filter) {
        UsernamePasswordAuthenticationToken userData = authenticationFacade.getAuthentication(); // получаем пользователя
        String userName = userData.getName();
        String userRole = userData.getAuthorities().toString();
        //UsernamePasswordAuthenticationToken dataUser = (UsernamePasswordAuthenticationToken) authentication;
        if(Objects.equals(column, "group")){
            column = "group_of_students";
        }
        if(Objects.equals(column, "phoneNumber")){
            column = "phone_number";
        }

        Sort sort = Objects.equals(direction, "") ? Sort.by("fio") : Sort.by(Sort.Direction.fromString(direction), column);//если не указано направление, то сортируем по fio
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentService.getStudents(filter, pageable, userName, userRole);
    }

    @GetMapping("length")
    public Number getFullLength(@RequestParam(name = "filter") String filter) {
        return studentRepository.getLengthStudents(filter);
    }

    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
