package dev.check;

import dev.check.entity.Password;
import dev.check.entity.Role;
import dev.check.entity.Student;
import dev.check.entity.User;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public void initial() {
        for(long i = 0L; i <= 100L; i ++) {
                studentRepository.save(new Student(i, "fio" + i, "def_group" + (i * 10), "+89" + (100 - i)));
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
