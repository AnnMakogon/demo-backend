package dev.check.mapper;

import dev.check.DTO.StudentFullTable;
import dev.check.DTO.StudentRegistr;
import dev.check.DTO.StudentUpdate;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.StudentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-28T12:41:48+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentEntity studentDtoToStudent(StudentUpdate studentDtoUpd) {
        if ( studentDtoUpd == null ) {
            return null;
        }

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId( studentDtoUpd.getId() );
        studentEntity.setFio( studentDtoUpd.getFio() );
        if ( studentDtoUpd.getGroup() != null ) {
            studentEntity.setGroup( Float.parseFloat( studentDtoUpd.getGroup() ) );
        }
        studentEntity.setPhoneNumber( studentDtoUpd.getPhoneNumber() );
        if ( studentDtoUpd.getCourse() != null ) {
            studentEntity.setCourse( Integer.parseInt( studentDtoUpd.getCourse() ) );
        }
        if ( studentDtoUpd.getDepartmentName() != null ) {
            studentEntity.setDepartmentName( Enum.valueOf( DepartmentName.class, studentDtoUpd.getDepartmentName() ) );
        }

        return studentEntity;
    }

    @Override
    public StudentEntity studentDtoRegistrToStudent(StudentRegistr studentDtoRegis) {
        if ( studentDtoRegis == null ) {
            return null;
        }

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId( studentDtoRegis.getId() );
        studentEntity.setFio( studentDtoRegis.getFio() );
        if ( studentDtoRegis.getGroup() != null ) {
            studentEntity.setGroup( Float.parseFloat( studentDtoRegis.getGroup() ) );
        }
        studentEntity.setPhoneNumber( studentDtoRegis.getPhoneNumber() );
        if ( studentDtoRegis.getCourse() != null ) {
            studentEntity.setCourse( Integer.parseInt( studentDtoRegis.getCourse() ) );
        }
        if ( studentDtoRegis.getDepartmentName() != null ) {
            studentEntity.setDepartmentName( Enum.valueOf( DepartmentName.class, studentDtoRegis.getDepartmentName() ) );
        }

        return studentEntity;
    }

    @Override
    public StudentFullTable studentEntityToStudentFullTable(StudentEntity student) {
        if ( student == null ) {
            return null;
        }

        StudentFullTable studentFullTable = new StudentFullTable();

        studentFullTable.setId( student.getId() );
        studentFullTable.setFio( student.getFio() );
        if ( student.getGroup() != null ) {
            studentFullTable.setGroup( String.valueOf( student.getGroup() ) );
        }
        studentFullTable.setPhoneNumber( student.getPhoneNumber() );
        studentFullTable.setCourse( student.getCourse() );
        if ( student.getDepartmentName() != null ) {
            studentFullTable.setDepartmentName( student.getDepartmentName().name() );
        }

        return studentFullTable;
    }

    @Override
    public List<StudentFullTable> studentEntityListToStudentDtoFull(List<StudentEntity> studentEntityList) {
        if ( studentEntityList == null ) {
            return null;
        }

        List<StudentFullTable> list = new ArrayList<StudentFullTable>( studentEntityList.size() );
        for ( StudentEntity studentEntity : studentEntityList ) {
            list.add( studentEntityToStudentFullTable( studentEntity ) );
        }

        return list;
    }
}
