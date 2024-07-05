package dev.check.mapper;

import dev.check.DTO.StudentFullTableDTO;
import dev.check.DTO.StudentRegistrDTO;
import dev.check.DTO.StudentTableDTO;
import dev.check.DTO.StudentUpdateDTO;
import dev.check.entity.Password;
import dev.check.entity.Student;
import dev.check.entity.User;
import org.mapstruct.*;
import dev.check.entity.Role;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    public StudentTableDTO studentToStudentDto(Student student);
    public  List<StudentTableDTO> studentListToStudentDtoList(List<Student> students);

    public  Student studentDtoToStudent(StudentUpdateDTO studentDtoUpd);
    public List<Student> studentDtoListToStudentList(List<StudentTableDTO> students);

    public Student studentDtoAuthToStudent(StudentRegistrDTO studentDtoAuth);
    public List<Student> studentDtoAuthListToStudentList(List<StudentRegistrDTO> studentDtoAuthList);

    public StudentFullTableDTO studentToStudentDtoFrontFull (Student student);
    public  List<StudentFullTableDTO> studentListToStudentDtoFullList(List<Student> students);

}
