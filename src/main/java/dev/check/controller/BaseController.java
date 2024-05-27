package dev.check.controller;

import dev.check.CustomExceptionHandler1;
import dev.check.entity.Student;
import dev.check.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CustomExceptionHandler1
@RequestMapping("/api/base")
public class BaseController {
    @Autowired // внедрение зависимостей
    public StudentRepository studentRepository;

    public List<Student> getStudentsList() {
        List<Student> list = StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return list;
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent) {
        return addStudent(newStudent);
    }

    private Student addStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent) {
        return updateStudent(changingStudent);
    }

    @Transactional
    private Student updateStudent(Student student) {
        if(student.getId() == null){
            throw new RuntimeException("id of changing student cannot be null");
        }

        Student changingStudent = getStudentsList().stream()
                .filter(el -> Objects.equals(el.getId(), student.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("student with id: " + student.getId() + "was not found"));

        changingStudent.setId(student.getId());
        changingStudent.setFio(student.getFio());
        changingStudent.setGroup(student.getGroup());
        changingStudent.setPhoneNumber(student.getPhoneNumber());
        studentRepository.save(changingStudent);

        return student;
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        return removeStudent(id);
    }

    private Long removeStudent(Long id) {
        studentRepository.deleteById(id);
        return id;
    }

    @GetMapping ("students")
    public List<Student> getStudentsPagSortFilter(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "size") int size,
                                        @RequestParam(name = "column") String column,
                                        @RequestParam(name = "direction") String direction,
                                        @RequestParam(name = "filter") String filter) {
        if(Objects.equals(column, "group")){         //переопределение колонок для корректного запроса в бд
            column = "group_of_students";
        }
        if(Objects.equals(column, "phoneNumber")){  //переопределение колонок для корректного запроса в бд
            column = "phone_number";
        }

        Sort sort = Objects.equals(direction, "") ? Sort.by("fio") : Sort.by(Sort.Direction.fromString(direction), column);//если не указано направление, то сортируем по fio
                                                                                                            //если есть направление - делаем из него и солоны - Sort
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Student> students = studentRepository.getStudents(filter, pageable);

        List<Student> stds = students.stream()
                .collect(Collectors.toList());
        return stds;
    }

    private int getLengthStudents() {
        return studentRepository.getLengthStudents();
    }

    @GetMapping("length")
    public Number getFullLength() {
        return getLengthStudents();
    }

    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
