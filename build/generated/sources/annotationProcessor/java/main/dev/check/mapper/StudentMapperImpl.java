package dev.check.mapper;

import dev.check.DTO.StudentFullTableDTO;
import dev.check.DTO.StudentRegistrDTO;
import dev.check.DTO.StudentTableDTO;
import dev.check.DTO.StudentUpdateDTO;
import dev.check.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T12:54:06+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentTableDTO studentToStudentDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentTableDTO studentTableDTO = new StudentTableDTO();

        studentTableDTO.setId( student.getId() );
        studentTableDTO.setFio( student.getFio() );
        studentTableDTO.setGroup( student.getGroup() );

        return studentTableDTO;
    }

    @Override
    public List<StudentTableDTO> studentListToStudentDtoList(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentTableDTO> list = new ArrayList<StudentTableDTO>( students.size() );
        for ( Student student : students ) {
            list.add( studentToStudentDto( student ) );
        }

        return list;
    }

    @Override
    public Student studentDtoToStudent(StudentUpdateDTO studentDtoUpd) {
        if ( studentDtoUpd == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDtoUpd.getId() );
        student.setFio( studentDtoUpd.getFio() );
        student.setGroup( studentDtoUpd.getGroup() );
        student.setPhoneNumber( studentDtoUpd.getPhoneNumber() );

        return student;
    }

    @Override
    public List<Student> studentDtoListToStudentList(List<StudentTableDTO> students) {
        if ( students == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( students.size() );
        for ( StudentTableDTO studentTableDTO : students ) {
            list.add( studentTableDTOToStudent( studentTableDTO ) );
        }

        return list;
    }

    @Override
    public Student studentDtoAuthToStudent(StudentRegistrDTO studentDtoAuth) {
        if ( studentDtoAuth == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDtoAuth.getId() );
        student.setFio( studentDtoAuth.getFio() );
        student.setGroup( studentDtoAuth.getGroup() );
        student.setPhoneNumber( studentDtoAuth.getPhoneNumber() );

        return student;
    }

    @Override
    public List<Student> studentDtoAuthListToStudentList(List<StudentRegistrDTO> studentDtoAuthList) {
        if ( studentDtoAuthList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( studentDtoAuthList.size() );
        for ( StudentRegistrDTO studentRegistrDTO : studentDtoAuthList ) {
            list.add( studentDtoAuthToStudent( studentRegistrDTO ) );
        }

        return list;
    }

    @Override
    public StudentFullTableDTO studentToStudentDtoFrontFull(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentFullTableDTO studentFullTableDTO = new StudentFullTableDTO();

        studentFullTableDTO.setId( student.getId() );
        studentFullTableDTO.setFio( student.getFio() );
        studentFullTableDTO.setGroup( student.getGroup() );
        studentFullTableDTO.setPhoneNumber( student.getPhoneNumber() );

        return studentFullTableDTO;
    }

    @Override
    public List<StudentFullTableDTO> studentListToStudentDtoFullList(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentFullTableDTO> list = new ArrayList<StudentFullTableDTO>( students.size() );
        for ( Student student : students ) {
            list.add( studentToStudentDtoFrontFull( student ) );
        }

        return list;
    }

    protected Student studentTableDTOToStudent(StudentTableDTO studentTableDTO) {
        if ( studentTableDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentTableDTO.getId() );
        student.setFio( studentTableDTO.getFio() );
        student.setGroup( studentTableDTO.getGroup() );

        return student;
    }
}
