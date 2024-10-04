package dev.check.service.InitializerAnyServices;

import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.*;
import dev.check.repositories.NewsletterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitialNewsletterWithAddress {

    private final NewsletterRepository newsletterRepository;

    public void initialNewsletterWithAddress(){
        // сохранение рассылки с адресом
        AddressEntity address = new AddressEntity(null, Role.STUDENT, new CourseEntity(null, CourseNumber.COURSE_3), null, null, null);
        //address.setRole(Role.STUDENT);
        //address.setCourse(3);

        GroupEntity group = new GroupEntity(null, GroupNumber.GROUP_1_3, null, Collections.singletonList(address));
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

        OffsetDateTime[] dates = new OffsetDateTime[110];

        OffsetDateTime currentTime = OffsetDateTime.now().plusSeconds(60);
        for (int i = 0; i < 110; i++) {
            dates[i] = currentTime.plusSeconds(30 * i);
        }

        DepartmentEntity department2 = new DepartmentEntity(DepartmentName.KFA);
        //departmentRepository.save(department2);

        DepartmentEntity department3 = new DepartmentEntity(DepartmentName.KFA);
        //departmentRepository.save(department3);

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
}
