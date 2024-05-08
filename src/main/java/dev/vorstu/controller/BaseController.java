package dev.vorstu.controller;

import dev.vorstu.entity.Student;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    @Autowired // внедрение зависимостей
    public StudentRepository studentRepository; //

    public ArrayList<Student> getStudentsList() {
        //pageable
        Iterator<Student> iterator = studentRepository.findAll().iterator();
        ArrayList<Student> newStudents = new ArrayList<Student>();
        while (iterator.hasNext()) {
            Student item = iterator.next();
            newStudents.add(item);
        }
        return newStudents;
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
    @GetMapping("students")
    public List<Student> getAllStudents() {
        List<Student> students = getStudentsList();

        /*Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student left, Student right) {
                return (int) (left.getId() - right.getId()); // use your logic
            }
        };*/
        students.sort(Comparator.comparing(Student::getId));

        Stream<Student> newStudents = students.stream();
        return newStudents
                //.sorted()
                .collect(Collectors.toList());
    }
    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
