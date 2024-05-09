package dev.vorstu.controller;

import dev.vorstu.entity.Student;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    private int callCount = 0;

    private String columnC = "id";
    private int callCount1 = 0;
    private int callCount2 = 0;
    private int callCount3 = 0;

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
    /*@GetMapping("students")
    public List<Student> getAllStudents() {
        List<Student> students = getStudentsList();

        !!!Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student left, Student right) {
                return (int) (left.getId() - right.getId()); // use your logic
            }
        };!!!
        students.sort(Comparator.comparing(Student::getId));

        Stream<Student> newStudents = students.stream();
        return newStudents
                //.sorted()
                .collect(Collectors.toList());
    }*/

    @GetMapping("students")
    public List<Student> getStudentsPag(@RequestParam(name = "start") int start,
                                        @RequestParam(name = "end") int end,
                                        @RequestParam(name = "sort")String column) {
        List<Student> students = getStudentsList();
        List<Student> sortStudents = sortS(students, column);
        List<Student> subSortStudents = sortStudents.subList(start, end);

        if(Objects.equals(column, "undefined")){
            subSortStudents.sort(Comparator.comparing(Student::getId));
        }

        Stream<Student> newStudents = subSortStudents.stream();
        return newStudents
                //.sorted()
                .collect(Collectors.toList());
    }

    public List<Student> sortS(List<Student> students, String column) {
        callCount++;

        if(!this.columnC.equals(column)) {
            callCount = 1;
            this.columnC = column;
        }
        switch (callCount % 3) {
            case 1:
                sortStudent(students, column, true);
                callCount1++;
                break;
            case 2:
                sortStudent(students, column, false);
                callCount2++;
                break;
            case 0:
                callCount3++;
                break;
        }
        return students;
    }

    public static void sortStudent(List<Student> students, String column, boolean ascending) {
        if (!Objects.equals(column, "undefined")){
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    try {
                        Field field = Student.class.getDeclaredField(column);
                        field.setAccessible(true);
                        Comparable value1 = (Comparable) field.get(s1);
                        Comparable value2 = (Comparable) field.get(s2);
                        return ascending ? value1.compareTo(value2) : value2.compareTo(value1);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка в сортировке", e);
                    }
                }
            });
        }
    }

    @GetMapping("length")
    public Number getLength() {
        List<Student> students = getStudentsList();
        return students.toArray().length;
    }






    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
