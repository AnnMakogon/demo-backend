package dev.check.mapper;

import dev.check.dto.Student;
import dev.check.dto.StudentRegistr;
import dev.check.dto.StudentUpdate;
import dev.check.entity.*;
import dev.check.entity.EnumEntity.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    StudentEntity studentDtoToStudent(StudentUpdate studentDtoUpd);

    @Mapping(target = "department", source = "departmentName")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "group", source = "group")
    StudentEntity studentDtoRegistrToStudent(StudentRegistr studentDtoRegis);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "departmentName", source = "departmentName")
    DepartmentEntity mapDepartmentEntity(StudentRegistr studentRegistr);
    DepartmentEntity mapDep(String value);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "courseNumber", source = "course")
    CourseEntity mapCourseEntity(StudentRegistr studentRegistr);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "groupValue", source = "group")
    GroupEntity mapGroupEntity(StudentRegistr studentRegistr);

    @Mapping(target = "departmentName", source = "student.department.departmentName")
    @Mapping(target = "group", source = "student.group.groupValue")
    @Mapping(target = "course", source = "student.course.courseNumber")
    Student studentEntityToStudent(StudentEntity student);

    List<Student> studentEntityListToStudentList(List<StudentEntity> studentEntityList);
    GroupEntity map(String value);
    String map(GroupEntity value);

    CourseEntity mapCourse(String value);

    String map(CourseEntity value);

}
