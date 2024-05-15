package dev.check.controller;

import dev.check.entity.Student;
import dev.check.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
// org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    private int callCount = 0;

    private String columnC = "id";

    private String filterWord;

    @Autowired // внедрение зависимостей
    public StudentRepository studentRepository;
    private Number length;

    private Page<Student> studPage;

    private List<Student> getStudentsFromSR(String substring, Pageable page){
        return studentRepository.getStudents(substring, page);
    }
    //list = this.getStudentsFromSR("", PageRequest.of(page, size, sortColumn(column)));

    private List<Student> getStudentsWithPage(String substring, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        this.studPage = studentRepository.findAll(PageRequest.of(page, size, sort));
        //return this.getStudentsFromSR(substring, this.studPage);
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

    private Sort sortOneColumn(String column){
        callCount++;
        if(!this.columnC.equals(column)) { //если до этого колонка была другая
            callCount = 1;                 // начинаем отсчет
            this.columnC = column;         //записываем нашу колонку
        }
        switch (callCount % 3) {           // остаток от деления на 3
            case 1:
                return Sort.by(column).ascending(); // по убыванию
            case 2:
                return Sort.by(column).descending();  //по возрастанию
            case 0:
                return Sort.by("id");// никак  = по id
        }
        return Sort.by("id");
    }

    private Sort sortColumn(String column) {
        if(Objects.equals(column, "")){  //если нам нужно ни по чем не сортировать
              return Sort.by("id");//просто берем отсортированный по id
        }else{                                 //если по чем-то сортируем
            if(Objects.equals(column, "nothing")){
                return Sort.by(this.columnC);
            }
            return this.sortOneColumn(column);//делаем сортировку по колонам
        }
    }

    @Transactional
    public List<Student> getPageSizeColumnFilter(int page, int size, String column, String filter) {
        List<Student> list;
        if (Objects.equals(column,"") && Objects.equals(filter,"") ) {               //если и сортировка и фильтр пусты - в самом начале
            //list = this.getStudentsFromSR("", PageRequest.of(page, size));
            list = getStudentsWithPage("", page, size, Sort.unsorted());
            list.forEach(e -> System.out.println(e.getFio() + " " + e.getId()));
            this.columnC = "";
        } else {
            if (Objects.equals(filter, "")) {                                        // если есть сортировка
                //list = this.getStudentsFromSR("", PageRequest.of(page, size, sortColumn(column))); // здесть вставить свою сортировку
                list = getStudentsWithPage("", page, size, sortColumn(column));
                list.forEach(e -> System.out.println(e.getFio() + " " + e.getId()));
            } else {
                if (Objects.equals(column, "")) {                                    // если есть фильтр
                    //list = this.getStudentsFromSR(filter, PageRequest.of(page, size));
                    list = getStudentsWithPage(filter, page, size, Sort.unsorted());
                    list.forEach(e -> System.out.println(e.getFio() + " " + e.getId()));
                    this.columnC = "";
                } else {
                    if (Objects.equals(column, "nothing")){                       // особый случай
                        //list = this.getStudentsFromSR("", PageRequest.of(page, size, sortColumn(column)));
                        //list = this.getStudentsFromSR("", PageRequest.of(page, size, sortColumn(column)));
                        list = getStudentsWithPage("", page, size, sortColumn(column));
                        list = getStudentsWithPage("", page, size, sortColumn(column));
                    }else {                                                        // если есть и сортировка и фильтр
                        //list = this.getStudentsFromSR(filter, PageRequest.of(page, size, sortColumn(column))); //здесь свою сортировку
                        list = getStudentsWithPage(filter, page, size, sortColumn(column));
                        list.forEach(e -> System.out.println(e.getFio() + " " + e.getId()));
                    }
                }
            }
        }
        return list;
    }

    @GetMapping("students")
    public List<Student> getStudentsPagSortFilter(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "size") int size,
                                        @RequestParam(name = "sort") String column,
                                        @RequestParam(name = "filter") String filter) {

        List<Student> students = getPageSizeColumnFilter(page, size, column, filter);

        List<Student> stds = students.stream()
                .collect(Collectors.toList());

        return stds;
    }

    @GetMapping("fulllength")            //эту вещь можно сделать через SQL и StudentRepository
    public Number getFullLength() {
        return getLengthStudents();
        //return this.studPage.getTotalElements();
    }

    public static List<Student> sortById(List<Student> students) {
        students.sort((st1, st2) ->
                st1.getId().compareTo(st2.getId()));
            return students;
    }

    public List<Student> filterS(List<Student> students, String filterWord){
        this.filterWord = filterWord;
        return students.stream()
                .filter(this::wordFilter)
                .collect(Collectors.toList());
    }

    private boolean wordFilter(Student student){
        return  student.getId().toString().equals(filterWord) ||
                student.getFio().contains(filterWord) ||
                student.getGroup().contains(filterWord) ||
                student.getPhoneNumber().contains(filterWord) ||
                student.getFio().equals(filterWord) ||
                student.getGroup().equals(filterWord) ||
                student.getPhoneNumber().equals(filterWord);
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
                break;
            case 2:
                sortStudent(students, column, false);
                break;
            case 0:
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
    public Number getLength(){
        return this.length;
    }

    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }

}
