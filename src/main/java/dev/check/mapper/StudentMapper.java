package dev.check.mapper;

import dev.check.dto.StudentFullTable;
import dev.check.dto.StudentRegistr;
import dev.check.dto.StudentUpdate;
import dev.check.entity.CourseEntity;
import dev.check.entity.GroupEntity;
import dev.check.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    public StudentEntity studentDtoToStudent(StudentUpdate studentDtoUpd);

    public StudentEntity studentDtoRegistrToStudent(StudentRegistr studentDtoRegis);

    @Mapping(target = "departmentName", source = "student.department.departmentName")
    @Mapping(target = "group", source = "student.group.groupValue")
    @Mapping(target = "course", source = "student.course.courseNumber")
    public StudentFullTable studentEntityToStudentFullTable(StudentEntity student);

    public List<StudentFullTable> studentEntityListToStudentDtoFull(List<StudentEntity> studentEntityList);
    public GroupEntity map(String value);
    public String map(GroupEntity value);

    public CourseEntity mapCourse(String value);

    public String map(CourseEntity value);

}
