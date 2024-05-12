package dev.check.controller;

import dev.check.entity.Student;
import dev.check.repositories.StudentRepository;
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

    private String filterWord;

    private List<Student> data;

    @Autowired // внедрение зависимостей
    public StudentRepository studentRepository;
    private Number length;

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
    public List<Student> getStudentsPagSortFilter(@RequestParam(name = "start") int start,
                                        @RequestParam(name = "end") int end,
                                        @RequestParam(name = "sort") String column,
                                        @RequestParam(name = "filter") String filter) {
        List<Student> students = new ArrayList<Student>();
        students = getStudentsList(); //записывание в лист полностью
        List<Student> filterStudents = new ArrayList<Student>();
        List<Student> filterStudentsById = new ArrayList<Student>();

        //ФИЛЬТР
        if(Objects.equals(filter, "")){   //вначале или когда фильтр пустой
            filterStudents = students;       //ничего с ним не делаем
            filterStudentsById = filterStudents; //просто передаем дальше в filterStudentsById
        }else{   //иначе, если мы по чем-то фильтруем
         filterStudents = this.filterS(students, filter);  //делаем фильтрацию
         filterStudentsById = sortById(filterStudents);    //делаем сортировку по id
        }

        //СОРТ
        List<Student> sortStudents = new ArrayList<Student>();
        if(Objects.equals(column, "")){  //если нам нужно ни по чем не сортировать
            sortStudents = filterStudentsById;  //просто берем отсортированный по id
        }else{                                  //если по чем-то сортируем
        sortStudents = sortS(filterStudentsById, column);  //делаем сортировку по колонам
        }

        List<Student> subSortStudents = new ArrayList<Student>();
        //ПАГИНАЦИЯ
        if(Objects.equals(filter, "") ) {                 //если мы ни по чем не фильтруем, то идем до конца страницы
            subSortStudents = sortStudents.subList(start, end); //обрезка для пагинации
        }else{
            if (sortStudents.size() < (end -(end - start))) {//если по чем-то фильтруем и список короче размера страницы, то идем до конца всего списка
                subSortStudents = sortStudents.subList(start, sortStudents.size());
            } else { //если мы по чем-то фильтруем и список больше страницы, то идем до конца страницы
                subSortStudents = sortStudents.subList(start, end);
            }
        }

        this.data = sortStudents;

        this.length = sortStudents.size();

        Stream<Student> newStudents = subSortStudents.stream();

        return newStudents
                //.sorted()
                .collect(Collectors.toList());
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

    @GetMapping("fulllength")
    public Number getFullLength() {
        List<Student> students = getStudentsList();
        return students.toArray().length;
    }

    public Number getRealLength(){
        if(!Objects.equals(this.getLength(), this.data.size())){
            return this.data.size();
        }
        return getLength();
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
