package dev.check.controller;

import dev.check.CustomExceptionHandler1;
import dev.check.entity.Student;
import dev.check.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CustomExceptionHandler1
public class FcudController {

    @Autowired
    public StudentRepository studentRepository;

    @GetMapping(value = "/test/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getAllTest() {
        Iterator<Student> iterator = studentRepository.findAll().iterator();
        ArrayList<Student> newStudents = new ArrayList<Student>();
        while (iterator.hasNext()) {
            Student item = iterator.next();
            newStudents.add(item);
        }
        return newStudents;
    }

    /*@GetMapping(value = "/test/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getAllTest1(@RequestParam(name = "page") int page,
                                     @RequestParam(name = "size") int size,
                                     @RequestParam(name = "column") String column,
                                     @RequestParam(name = "direction") String direction,
                                     @RequestParam(name = "filter") String filter)
        throws IllegalArgumentException {

        List<Student> students = getPageSizeColumnFilter(page, size, column, direction, filter);

        return students.stream()
                .collect(Collectors.toList());
    }*/

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

    private List<Student> getStudentsFromSR(String substring, Pageable page){
        return studentRepository.getStudents(substring, page);
    }

    private List<Student> getStudentsWithPage( int page, int size, Sort sort, String substring) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return  this.getStudentsFromSR(substring, pageable);
    }

    @GetMapping(value = "/test/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getIdTest(@PathVariable("id") Long id){
        return studentRepository.findById(id).get();
    }

    @PostMapping(value = "/test/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent) {
        return studentRepository.save(newStudent);
    }

    @PutMapping(value = "/test/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student update(@RequestBody Student changingStudent) {
        return studentRepository.save(changingStudent);
    }

    @DeleteMapping(value = "/test/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") Long id) {
        studentRepository.deleteById(id);
    }



}
