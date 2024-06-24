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
        for(long i = 0L; i <= 100; i ++) {
                studentRepository.save(new Student(i, FIO [(int) i], (i * 10) + " def_group" , "+89" + (100 - i)));
        }

        Iterable<Student> itr = studentRepository.findAll();
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
        User admin = new User(
                null,
                "Admin",
                Role.ADMIN,
                new Password("1234"),
                true
        );
        userRepository.save(admin);
    }

    public static final String[] FIO = {
            "Алла Борисовна","student", "Admin", "Петров Иван Николаевич", "Сидорова Мария Ивановна",
            "Кузнецова София Леонидовна", "Попова Екатерина Дмитриевна", "Васильев Дмитрий Петрович",
            "Соколов Максим Игоревич", "Михайлова Анна Васильевна", "Федорова Алина Максимовна",
            "Морозов Никита Сергеевич", "Волкова Ольга Николаевна", "Алексеева Наталья Дмитриевна",
            "Лебедева Елена Петровна", "Семенова Дарья Максимовна", "Егоров Артем Владимирович",
            "Павлова Виктория Алексеевна", "Козлова Ксения Михайловна", "Степанов Антон Павлович",
            "Николаев Андрей Михайлович", "Орлова Юлия Сергеевна", "Андреев Сергей Владимирович",
            "Макаров Владимир Александрович", "Никитин Олег Петрович", "Захарова Татьяна Ивановна",
            "Зайцева Лариса Олеговна", "Соловьева Александра Геннадьевна", "Борисов Павел Дмитриевич",
            "Яковлев Денис Александрович", "Григорьев Егор Васильевич", "Романова Ирина Петровна",
            "Воробьева Валерия Андреевна", "Сергеев Константин Сергеевич", "Кузьмин Александр Иванович",
            "Фролов Михаил Владимирович", "Александров Василий Николаевич", "Дмитриев Роман Алексеевич",
            "Королев Игорь Владимирович", "Гусева Галина Николаевна", "Киселева Вера Александровна",
            "Ильина Надежда Михайловна", "Максимова Любовь Викторовна", "Полякова Валентина Игоревна",
            "Сорокина Светлана Петровна", "Виноградова Евгения Александровна", "Ковалева Жанна Павловна",
            "Белова Маргарита Константиновна", "Медведев Аркадий Юрьевич", "Антонова Оксана Владимировна",
            "Тарасова Елена Сергеевна", "Жукова Анна Максимовна", "Баранов Николай Васильевич",
            "Филиппова Ирина Геннадьевна", "Комарова Светлана Петровна", "Давыдова Мария Александровна",
            "Беляева Татьяна Николаевна", "Герасимова Екатерина Сергеевна", "Богданова Людмила Андреевна",
            "Осипов Дмитрий Игоревич", "Сидоренко Василий Александрович", "Матвеева Ольга Владимировна",
            "Титова Александра Ивановна", "Маркова Елена Дмитриевна", "Миронова Наталья Петровна",
            "Крылова Вера Михайловна", "Куликова Анна Васильевна", "Карпова Мария Геннадьевна",
            "Власова Екатерина Алексеевна", "Мельникова Ирина Владимировна", "Денисова Ольга Николаевна",
            "Гаврилова Светлана Петровна", "Тихонова Марина Юрьевна", "Казакова Елена Александровна",
            "Афанасьева Анна Васильевна", "Данилова Мария Сергеевна", "Савельева Ольга Игоревна",
            "Тимофеева Екатерина Александровна", "Фомина Наталья Дмитриевна", "Чернова Мария Владимировна",
            "Абрамова Елена Николаевна", "Мартынова Светлана Алексеевна", "Ефимова Анна Васильевна",
            "Федотова Ольга Петровна", "Щербакова Мария Александровна", "Назарова Екатерина Игоревна",
            "Калинина Наталья Дмитриевна", "Исаева Мария Владимировна", "Чернышева Елена Алексеевна",
            "Быкова Анна Николаевна", "Маслова Екатерина Васильевна", "Родионова Ольга Сергеевна",
            "Коновалова Мария Александровна", "Лазарева Екатерина Ивановна", "Воронина Наталья Петровна",
            "Климова Мария Владимировна", "Филатова Светлана Николаевна", "Пономарева Ольга Алексеевна",
            "Голубева Екатерина Дмитриевна", "Кудрявцева Мария Васильевна", "Прокофьева Анна Петровна",
            "Наумова Елена Игоревна", "Потапова Мария Александровна"
    };
}
