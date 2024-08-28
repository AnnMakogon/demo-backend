package dev.check.mapper;

import dev.check.DTO.StudentFullTable;
import dev.check.DTO.StudentRegistr;
import dev.check.DTO.StudentUpdate;
import dev.check.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    public StudentEntity studentDtoToStudent(StudentUpdate studentDtoUpd);

    public StudentEntity studentDtoRegistrToStudent(StudentRegistr studentDtoRegis);

    StudentFullTable studentEntityToStudentFullTable(StudentEntity student);
    List<StudentFullTable> studentEntityListToStudentDtoFull(List<StudentEntity> studentEntityList);

}
