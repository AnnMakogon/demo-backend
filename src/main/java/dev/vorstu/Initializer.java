package dev.vorstu;

import dev.vorstu.entity.Password;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public void initial() {
        for(long i = 0L; i < 100L; i ++) {
            studentRepository.save(new Student(i,"fio" + i,"def_group","+89"));
        }

        Iterable<Student> itr = studentRepository.findAll();// возвращает Iterable данного типа
        for (Student item : itr) {
            System.out.println(item);
        }
        System.out.println("");

        User student = new User(
                null,
                "student",
                Role.STUDENT,
                new Password("1234"),
                true
        );
        userRepository.save(student);
    }
}
