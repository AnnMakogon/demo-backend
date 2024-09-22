package dev.check.service;

import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.*;
import dev.check.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@Slf4j
public class InitializerService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private void initialStudent(){
        //////////////////   STUDENTы со связью с USERами, кафедрой, группой  ///////////////////

        // заполнение с учетом схемы: На 1 курсе: 1.1, 1.2, 1.3, 2.1, 3.1, 3.2,      2-3 курс: kfa: 1.1, 1.2, 1.3,  kma: 2.1, kucp: 3.1, 3.2
        for (long j = 0L; j <= 5; j++) {
            StudentEntity student0 = new StudentEntity(
                    null,
                    FIO[(int) (j % FIO.length)],
                    new GroupEntity(GroupNumber.GROUP_1_1),
                    "+89" + (100 - j),
                    1,
                    null,
                    null
            );
            UserEntity user0 = new UserEntity(
                    "student " + j,
                    Role.STUDENT,
                    new PasswordEntity("1234"),
                    true,
                    "myworkemail033@mail.ru",
                    student0
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
                    1,
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
                    student0
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
                    1,
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
                    student0
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
                    1,
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
                    student0
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
                    1,
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
                    student0
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
                    1,
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
                    student0
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
                    2,
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
                    student0
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
                    2,
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
                    student0
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
                    2,
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
                    student0
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
                    2,
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
                    student0
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
                    2,
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
                    student0
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
                    2,
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
                    student0
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
                    3,
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
                    student0
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
                    3,
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
                    student0
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
                    3,
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
                    student0
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
                    3,
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
                    student0
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
                    3,
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
                    student0
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
                    3,
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
                    student0
            );
            userRepository.save(user0);

            student0.setUser(user0);
            studentRepository.save(student0);
        }// 3 3.2
    }

    private void initialNewsletterWithAddress(){
        // сохранение рассылки с адресом
        AddressEntity address = new AddressEntity(null, Role.STUDENT, new CourseEntity(null, CourseNumber.COURSE_3), null, null, null);
        //address.setRole(Role.STUDENT);
        //address.setCourse(3);

        GroupEntity group = new GroupEntity(null, GroupNumber.GROUP_1_3, address, null, null);
        //group.setGroupValue(1.3f);
        //group.setAddress(address);
        //groupRepository.save(group);

        List<GroupEntity> groups = new ArrayList<>();
        groups.add(group);

        GroupEntity group1 = new GroupEntity(GroupNumber.GROUP_3_3);
        //groupRepository.save(group1);
        AddressGroupEntity addressGroup = new AddressGroupEntity(null, address, group1);
        //addressGroup.setAddress(address);
        //addressGroup.setGroup(group1);

        List<AddressGroupEntity> addGroups = new ArrayList<>();
        addGroups.add(addressGroup);
        address.setGroups(addGroups);

        DepartmentEntity department = new DepartmentEntity(DepartmentName.KFA);
        //departmentRepository.save(department);
        AddressDepartmentEntity addressDepartment = new AddressDepartmentEntity(null, address, department );
        //addressDepartment.setAddress(address);
        //addressDepartment.setDepartment(department);

        List<AddressDepartmentEntity> departments = new ArrayList<>();
        departments.add(addressDepartment);

        address.setDepartments(departments);

        GroupEntity group11 = new GroupEntity(GroupNumber.GROUP_1_3);
        //groupRepository.save(group11);
        //address.setGroupStudent(Collections.singletonList(group11));

        NewsletterEntity newsletter = new NewsletterEntity(null, OffsetDateTime.now().plusSeconds(30),
                "This is the newsletter text.", "Newsletter Subject",
                null, false, Status.NOTSENT );
        //newsletter.setDate(OffsetDateTime.now().plusSeconds(30));
        //newsletter.setText("This is the newsletter text.");
        //newsletter.setSubject("Newsletter Subject");
        //newsletter.setSent(false);
        //newsletter.setStatus(Status.NOTSENT);

        address.setNewsletter(newsletter);

        newsletterRepository.save(newsletter);

        //addressRepository.save(address);
    }

    private void initialAdmin(){
        ///////    ADMIN без связи со студентом, логично //////
        UserEntity admin = new UserEntity(
                "Admin",
                Role.ADMIN,
                new PasswordEntity("1234"),
                true,
                "myworkemail033@mail.ru"
        );
        userRepository.save(admin);
    }

    private  void initialStudentWithUserRelation(){
        //сохранение студента со связью с юзером
        StudentEntity student = new StudentEntity(
                null,
                "student",
                new GroupEntity(GroupNumber.GROUP_3_3),
                "+890000001",
                3,
                new DepartmentEntity(DepartmentName.KUCP),
                null
        );

        UserEntity studentU = new UserEntity(
                "student",
                Role.STUDENT,
                new PasswordEntity("1234"),
                true,
                "myworkemail033@mail.ru",
                student
        );
        userRepository.save(studentU);

        student.setUser(studentU);
        studentRepository.save(student);
    }

    private void initialNewsletters(){

        OffsetDateTime[] dates = new OffsetDateTime[110];

        OffsetDateTime currentTime = OffsetDateTime.now().plusSeconds(60);
        for (int i = 0; i < 110; i++) {
            dates[i] = currentTime.plusSeconds(30 * i);
        }

        DepartmentEntity department2 = new DepartmentEntity(DepartmentName.KFA);
        departmentRepository.save(department2);

        DepartmentEntity department3 = new DepartmentEntity(DepartmentName.KFA);
        departmentRepository.save(department3);

        AddressEntity add = new AddressEntity(null, Role.STUDENT, new CourseEntity(null, CourseNumber.COURSE_2), null, null, new ArrayList<>());
        AddressEntity add0 = new AddressEntity(null, Role.STUDENT,  new CourseEntity(null, CourseNumber.COURSE_2), null, null, new ArrayList<>());

        AddressDepartmentEntity addressDepartment1 = new AddressDepartmentEntity(null, add, department2);
        AddressDepartmentEntity addressDepartment2 = new AddressDepartmentEntity(null, add0, department3);

        add.getDepartments().add(addressDepartment1);
        add0.getDepartments().add(addressDepartment2);

        department2.getAddresses().add(addressDepartment1.getAddress());
        department3.getAddresses().add(addressDepartment2.getAddress());

        NewsletterEntity newsletterTest = new NewsletterEntity(
                null,
                OffsetDateTime.now().plusSeconds(40),
                "Proverka AAAAAAAAA",
                "Proverka subject",
                null,//addList,
                false,
                Status.NOTSENT
        );

        List<AddressEntity> newsletterAddresses = new ArrayList<>();
        newsletterAddresses.add(add);
        newsletterAddresses.add(add0);
        newsletterTest.setAddresses(newsletterAddresses);

        add.setNewsletter(newsletterTest);
        add0.setNewsletter(newsletterTest);

        newsletterRepository.save(newsletterTest);

        for (long i = 0L; i <= 50; i++) {
            newsletterRepository.save(new NewsletterEntity(
                    null,
                    dates[(int) i],
                    "Proverka" + i,
                    "Proverka subject" + i,
                    null,
                    false,
                    Status.NOTSENT)
            );
        }

        for (long i = 51L; i <= 70; i++) {
            newsletterRepository.save(new NewsletterEntity(
                    null,
                    dates[(int) i],
                    "Proverka" + i,
                    "Proverka subject" + i,
                    null,
                    false,
                    Status.NOTSENT)
            );
        }
        for (long i = 71L; i <= 85; i++) {
            newsletterRepository.save(new NewsletterEntity(
                    null,
                    dates[(int) i],
                    "Proverka" + i,
                    "Proverka subject" + i,
                    null,
                    false,
                    Status.ERROR)
            );
        }
        for (long i = 86L; i <= 100; i++) {
            newsletterRepository.save(new NewsletterEntity(
                    null,
                    dates[(int) i],
                    "Proverka" + i,
                    "Proverka subject" + i,
                    null,
                    true,
                    Status.SUCCESSFULLY)
            );
        }
    }

    @Transactional
    public void initial() {

        initialStudent();

        initialNewsletterWithAddress();

        initialAdmin();

        initialStudentWithUserRelation();

        initialNewsletters();

        log.info("DB initialization completed");
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
