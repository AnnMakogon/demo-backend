package dev.check.controller;

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
@RequestMapping("/api/base")
public class BaseController {

    private int callCount = 0;
    private String columnC = "id";
    private String filterWord;
    private Number length;

    @Autowired // внедрение зависимостей
    public StudentRepository studentRepository;

    private List<Student> getStudentsFromSR(String substring, Pageable page){
        return studentRepository.getStudents(substring, page);
    }

    private List<Student> getStudentsWithPage( int page, int size, Sort sort, String substring) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return  this.getStudentsFromSR(substring, pageable);
    }

    private int getLengthStudents() {
        return studentRepository.getLengthStudents();
    }

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
        studentRepository.save(changingStudent); // после этого меняется порядок data

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

    @Transactional
    public List<Student> getPageSizeColumnFilter(int page, int size, String column, String direction, String filter) {   // ТУТ ПЕРЕДЕЛАТЬ ВСЕ С СОРТИРОВКОЙ
        if(Objects.equals(column, "group")){
            column = "group_of_students";
        }
        if(Objects.equals(column, "phoneNumber")){
            column = "phone_number";
        }
        Sort sort;
        if(Objects.equals(direction, "")) {
            sort = Sort.by("id");
        } else {
            Sort.Direction direct = Sort.Direction.fromString(direction);
            sort = Sort.by(direct, column);}
        return getStudentsWithPage(page, size, sort, filter);
    }

    @GetMapping("students")
    public List<Student> getStudentsPagSortFilter(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "size") int size,
                                        @RequestParam(name = "sort") String column,
                                        @RequestParam(name = "direction") String direction,  //проверить
                                        @RequestParam(name = "filter") String filter) {

        List<Student> students = getPageSizeColumnFilter(page, size, column, direction, filter);

        List<Student> stds = students.stream()
                .collect(Collectors.toList());
        return stds;
    }

    @GetMapping("fulllength")
    public Number getFullLength() {
        return getLengthStudents();
    }

    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
