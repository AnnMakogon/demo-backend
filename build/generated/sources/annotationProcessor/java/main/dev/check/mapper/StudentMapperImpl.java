package dev.check.mapper;

import dev.check.dto.StudentFullTable;
import dev.check.dto.StudentRegistr;
import dev.check.dto.StudentUpdate;
import dev.check.entity.CourseEntity;
import dev.check.entity.DepartmentEntity;
import dev.check.entity.EnumEntity.CourseNumber;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.GroupNumber;
import dev.check.entity.GroupEntity;
import dev.check.entity.StudentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-22T22:11:49+0300",
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
        studentEntity.setGroup( map( studentDtoUpd.getGroup() ) );
        studentEntity.setPhoneNumber( studentDtoUpd.getPhoneNumber() );
        studentEntity.setCourse( mapCourse( studentDtoUpd.getCourse() ) );

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
        studentEntity.setGroup( map( studentDtoRegis.getGroup() ) );
        studentEntity.setPhoneNumber( studentDtoRegis.getPhoneNumber() );
        studentEntity.setCourse( mapCourse( studentDtoRegis.getCourse() ) );

        return studentEntity;
    }

    @Override
    public StudentFullTable studentEntityToStudentFullTable(StudentEntity student) {
        if ( student == null ) {
            return null;
        }

        StudentFullTable studentFullTable = new StudentFullTable();

        DepartmentName departmentName = studentDepartmentDepartmentName( student );
        if ( departmentName != null ) {
            studentFullTable.setDepartmentName( departmentName.name() );
        }
        GroupNumber groupValue = studentGroupGroupValue( student );
        if ( groupValue != null ) {
            studentFullTable.setGroup( groupValue.name() );
        }
        CourseNumber courseNumber = studentCourseCourseNumber( student );
        if ( courseNumber != null ) {
            studentFullTable.setCourse( courseNumber.name() );
        }
        studentFullTable.setId( student.getId() );
        studentFullTable.setFio( student.getFio() );
        studentFullTable.setPhoneNumber( student.getPhoneNumber() );

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

    @Override
    public GroupEntity map(String value) {
        if ( value == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        return groupEntity;
    }

    @Override
    public String map(GroupEntity value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }

    @Override
    public CourseEntity mapCourse(String value) {
        if ( value == null ) {
            return null;
        }

        CourseEntity courseEntity = new CourseEntity();

        return courseEntity;
    }

    @Override
    public String map(CourseEntity value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }

    private DepartmentName studentDepartmentDepartmentName(StudentEntity studentEntity) {
        if ( studentEntity == null ) {
            return null;
        }
        DepartmentEntity department = studentEntity.getDepartment();
        if ( department == null ) {
            return null;
        }
        DepartmentName departmentName = department.getDepartmentName();
        if ( departmentName == null ) {
            return null;
        }
        return departmentName;
    }

    private GroupNumber studentGroupGroupValue(StudentEntity studentEntity) {
        if ( studentEntity == null ) {
            return null;
        }
        GroupEntity group = studentEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        GroupNumber groupValue = group.getGroupValue();
        if ( groupValue == null ) {
            return null;
        }
        return groupValue;
    }

    private CourseNumber studentCourseCourseNumber(StudentEntity studentEntity) {
        if ( studentEntity == null ) {
            return null;
        }
        CourseEntity course = studentEntity.getCourse();
        if ( course == null ) {
            return null;
        }
        CourseNumber courseNumber = course.getCourseNumber();
        if ( courseNumber == null ) {
            return null;
        }
        return courseNumber;
    }
}
