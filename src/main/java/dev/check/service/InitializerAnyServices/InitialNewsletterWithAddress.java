package dev.check.service.InitializerAnyServices;

import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.*;
import dev.check.repositories.CourseRepository;
import dev.check.repositories.DepartmentRepository;
import dev.check.repositories.GroupRepository;
import dev.check.repositories.NewsletterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitialNewsletterWithAddress {

    private final NewsletterRepository newsletterRepository;

    private final DepartmentRepository departmentRepository;

    private final CourseRepository courseRepository;

    private final GroupRepository groupRepository;

    public void initialNewsletterWithAddress(){
        // сохранение рассылки с адресом
        AddressEntity address = new AddressEntity((Long) null, Role.STUDENT, null, null, null, null);

        GroupEntity group1 = new GroupEntity(GroupNumber.GROUP_3_3);
        AddressGroupEntity addressGroup = new AddressGroupEntity(null, address, group1);
        List<AddressGroupEntity> addGroups = new ArrayList<>();
        addGroups.add(addressGroup);
        address.setGroups(addGroups);

        CourseEntity course1 = new CourseEntity(CourseNumber.COURSE_1);
        AddressCourseEntity addressCourse = new AddressCourseEntity(null, address, course1);
        List<AddressCourseEntity> addCourse = new ArrayList<>();
        addCourse.add(addressCourse);
        address.setCourses(addCourse);

        DepartmentEntity department1 = new DepartmentEntity(DepartmentName.KFA);
        AddressDepartmentEntity addressDepartment = new AddressDepartmentEntity(null, address, department1 );
        List<AddressDepartmentEntity> departments = new ArrayList<>();
        departments.add(addressDepartment);
        address.setDepartments(departments);


        NewsletterEntity newsletter = new NewsletterEntity(null, OffsetDateTime.now().plusSeconds(30),
                "This is the newsletter text.", "Newsletter Subject",
                null, false, Status.NOTSENT );

        address.setNewsletter(newsletter);
        newsletterRepository.save(newsletter);


        OffsetDateTime[] dates = new OffsetDateTime[110];

        OffsetDateTime currentTime = OffsetDateTime.now().plusSeconds(60);
        for (int i = 0; i < 110; i++) {
            dates[i] = currentTime.plusSeconds(30 * i);
        }
        //сохранение адреса с заполнением вспомогательных таблиц
        AddressEntity add = new AddressEntity(Role.STUDENT);
        AddressEntity add0 = new AddressEntity(Role.STUDENT);

        // кафедра
        DepartmentEntity department2 = new DepartmentEntity(DepartmentName.KFA);
        departmentRepository.save(department2);
        DepartmentEntity department3 = new DepartmentEntity(DepartmentName.KFA);
        departmentRepository.save(department3);

        AddressDepartmentEntity addressDepartment1 = new AddressDepartmentEntity(null, add, department2);
        AddressDepartmentEntity addressDepartment2 = new AddressDepartmentEntity(null, add0, department3);

        add.getDepartments().add(addressDepartment1);
        add0.getDepartments().add(addressDepartment2);

        department2.getAddresses().add(addressDepartment1.getAddress());
        department3.getAddresses().add(addressDepartment2.getAddress());

        //курс
        CourseEntity course2 = new CourseEntity(CourseNumber.COURSE_1);
        courseRepository.save(course2);
        CourseEntity course3 = new CourseEntity(CourseNumber.COURSE_2);
        courseRepository.save(course3);

        AddressCourseEntity addressCourse1 = new AddressCourseEntity(null, add, course2);
        AddressCourseEntity addressCourse2 = new AddressCourseEntity(null, add0, course3);

        add.getCourses().add(addressCourse1);
        add0.getCourses().add(addressCourse2);

        course2.getAddresses().add(addressCourse1.getAddress());
        course3.getAddresses().add(addressCourse2.getAddress());

        // группа
        GroupEntity group2 = new GroupEntity(GroupNumber.GROUP_3_2);
        groupRepository.save(group2);
        GroupEntity group3 = new GroupEntity(GroupNumber.GROUP_3_1);
        groupRepository.save(group3);

        AddressGroupEntity addressGroup1 = new AddressGroupEntity(null, add, group2);
        AddressGroupEntity addressGroup2 = new AddressGroupEntity(null, add, group3);

        add.getGroups().add(addressGroup1);
        add0.getGroups().add(addressGroup2);

        group2.getAddresses().add(addressGroup1.getAddress());
        group3.getAddresses().add(addressGroup2.getAddress());


        NewsletterEntity newsletterTest = new NewsletterEntity(
                null,
                OffsetDateTime.now().plusSeconds(40),
                "Proverka AAAAAAAAA",
                "Proverka subject",
                null,
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
}

