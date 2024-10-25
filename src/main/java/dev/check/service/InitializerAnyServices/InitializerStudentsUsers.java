package dev.check.service.InitializerAnyServices;

import dev.check.entity.*;
import dev.check.entity.EnumEntity.CourseNumber;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.GroupNumber;
import dev.check.entity.EnumEntity.Role;
import dev.check.repositories.StudentRepository;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitializerStudentsUsers {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    public void initialAdmin(){
        ///////    ADMIN без связи со студентом, логично //////
        UserEntity admin = new UserEntity(
                "Admin",
                Role.ADMIN,
                new PasswordEntity("1234"),
                true,
                "myworkemail033@mail.ru",
                true
        );
        userRepository.save(admin);
    }
    public void initialStudentsUsers(){
        //////////////////   STUDENTы со связью с USERами, кафедрой, группой  ///////////////////

        // заполнение с учетом схемы: На 1 курсе: 1.1, 1.2, 1.3, 2.1, 3.1, 3.2,      2-3 курс: kfa: 1.1, 1.2, 1.3,  kma: 2.1, kucp: 3.1, 3.2
        for (long j = 0L; j <= 5; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }  // 1 1.1
        for (long j = 6L; j <= 10; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        } // 1 1.2
        for (long j = 11L; j <= 15; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_3),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 1 1.3
        for (long j = 16L; j <= 20; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_2_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 1 2.1
        for (long j = 21L; j <= 25; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 1 3.1
        for (long j = 26L; j <= 30; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_1),
                    null,
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 1 3.2

        for (long j = 31L; j <= 35; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);

            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 1.1
        for (long j = 36L; j <= 40; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 1.2
        for (long j = 41L; j <= 45; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_3),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 1.3
        for (long j = 46L; j <= 50; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_2_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KMA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 2.1
        for (long j = 51L; j <= 55; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KUCP),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 3.1
        for (long j = 56L; j <= 60; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_2),
                    new DepartmentEntity(DepartmentName.KUCP),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 2 3.2

        for (long j = 61L; j <= 65; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 1.1
        for (long j = 66L; j <= 70; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 1.2
        for (long j = 71L; j <= 75; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_3),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KFA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 1.3
        for (long j = 76L; j <= 80; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_2_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KMA),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 2.1
        for (long j = 81L; j <= 85; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_1),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KUCP),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 3.1
        for (long j = 86L; j <= 90; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_3_2),
                    "+89" + (100 - j),
                    new CourseEntity(CourseNumber.COURSE_3),
                    new DepartmentEntity(DepartmentName.KUCP),
                    null
            );
            studentRepository.save(student0);
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0,
                    true
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 3.2
    }
    // массив имен для студентов

    private static final String[] FIO = {
            "Алла Борисовна", "Петров Иван Николаевич", "Сидорова Мария Ивановна",
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
            "Наумова Елена Игоревна", "Потапова Мария Александровна", "Иван Петрович Иванов",
            "Мария Сергеевна Васильева", "Дмитрий Андреевич Кузнецов", "Анастасия Михайловна Смирнова",
            "Сергей Владимирович Петров", "Елена Николаевна Крылова", "Андрей Иванович Соловьев",
            "Ольга Александровна Романова", "Владимир Сергеевич Морозов", "Наталья Юрьевна Леонова"
    };

}
