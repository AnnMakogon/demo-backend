package dev.check.mapper;

import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsletterMapper {

    @Mapping(target = "addresses", ignore = true)
    NewsletterEntity newsletterToNewsletterEntity(Newsletter newsletter);

    @Named("mapRole")
    Role mapRole(String role);

    @Named("mapDepartment")
    DepartmentName mapDepartment(String departmentName);

    @Mappings({
            @Mapping(source = "address.role", target = "role", qualifiedByName = "mapRole"),
            @Mapping(source = "address.courses", target = "courses", defaultExpression = "java(null)"),
            @Mapping(source = "address.departments", target = "departments", defaultExpression = "java(null)"),
            @Mapping(source = "groups", target = "groups", defaultExpression = "java(null)"),
            @Mapping(target = "newsletter", ignore = true)
    })
    AddressEntity addressToAddressEntity(Address address);

    @Mapping(target = ".", source = "departmentName")
    String departmentToString(DepartmentEntity department);

    List<String> departmentListToString (List<DepartmentEntity> departmentEntityList);

    @Mapping(target = "departmentName", source = ".")
    DepartmentEntity StringToDepartment(String dep);

    List<DepartmentEntity> listStringDepartmentToListDep(List<String> dep);

    Status stringToStatus(String status);

    NewsletterEntity newsletterDtoToNewsletter(Newsletter newsletterDto);

    @Mapping(source = "entity.addresses", target = "address")
    @Mapping(source = "entity.status", target = "status")
    Newsletter newsletterEntityToNewsletter(NewsletterEntity entity);

    @IterableMapping(elementTargetType = Address.class)
    List<Address> addressEntitiesToAddresses(List<AddressEntity> entities);

    @Mapping(source = "entity.role", target = "role")
    @Mapping(source = "entity.courses", target = "courses")
    @Mapping(source = "entity.groups", target = "groups")
    @Mapping(source = "entity.departments", target = "departments")
    Address addressEntityToAddress(AddressEntity entity);

    // курс
    @Named("mapCourse")
    default String mapCourse(AddressCourseEntity value) {
        return value.getCourse().getCourseNumber().getCourse();
    }
    @IterableMapping(qualifiedByName = "mapCourse")
    List<String> mapCourses(List<AddressCourseEntity> value);

    //группа
    @Named("mapGroup")
    default String mapGroup(AddressGroupEntity value) {
        return value.getGroup().getGroupValue().getGroup();
    }
    @IterableMapping(qualifiedByName = "mapGroup")
    List<String> mapGroups(List<AddressGroupEntity> value);

    //кафедра
    @Named("mapDepartment")
    default String mapDepartment(AddressDepartmentEntity value) {
        return value.getDepartment().getDepartmentName().toString();
    }
    @IterableMapping(qualifiedByName = "mapDepartment")
    List<String> mapDepartments(List<AddressDepartmentEntity> value);

    List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities);

    List<AddressDepartmentEntity> mapAddDepartmentList(List<String> value);
    AddressDepartmentEntity mapAddDepartment(String value);

    List<AddressGroupEntity> mapList(List<String> value);

    AddressGroupEntity mapGroup(String value);

    List<AddressCourseEntity> mapCourseList(List<String> value);

    AddressCourseEntity mapAddressCourse(String course);

    CourseEntity mapCourse(String value);
}
