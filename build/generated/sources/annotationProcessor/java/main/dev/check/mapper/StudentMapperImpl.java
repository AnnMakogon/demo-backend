package dev.check.mapper;

import dev.check.dto.Student;
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
    date = "2024-10-22T18:58:59+0300",
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

        studentEntity.setDepartment( mapDep( studentDtoRegis.getDepartmentName() ) );
        studentEntity.setCourse( mapCourse( studentDtoRegis.getCourse() ) );
        studentEntity.setGroup( map( studentDtoRegis.getGroup() ) );
        studentEntity.setId( studentDtoRegis.getId() );
        studentEntity.setFio( studentDtoRegis.getFio() );
        studentEntity.setPhoneNumber( studentDtoRegis.getPhoneNumber() );

        return studentEntity;
    }

    @Override
    public DepartmentEntity mapDepartmentEntity(StudentRegistr studentRegistr) {
        if ( studentRegistr == null ) {
            return null;
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        if ( studentRegistr.getDepartmentName() != null ) {
            departmentEntity.setDepartmentName( Enum.valueOf( DepartmentName.class, studentRegistr.getDepartmentName() ) );
        }

        return departmentEntity;
    }

    @Override
    public DepartmentEntity mapDep(String value) {
        if ( value == null ) {
            return null;
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        return departmentEntity;
    }

    @Override
    public CourseEntity mapCourseEntity(StudentRegistr studentRegistr) {
        if ( studentRegistr == null ) {
            return null;
        }

        CourseEntity courseEntity = new CourseEntity();

        if ( studentRegistr.getCourse() != null ) {
            courseEntity.setCourseNumber( Enum.valueOf( CourseNumber.class, studentRegistr.getCourse() ) );
        }

        return courseEntity;
    }

    @Override
    public GroupEntity mapGroupEntity(StudentRegistr studentRegistr) {
        if ( studentRegistr == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        if ( studentRegistr.getGroup() != null ) {
            groupEntity.setGroupValue( Enum.valueOf( GroupNumber.class, studentRegistr.getGroup() ) );
        }

        return groupEntity;
    }

    @Override
    public Student studentEntityToStudent(StudentEntity student) {
        if ( student == null ) {
            return null;
        }

        Student student1 = new Student();

        DepartmentName departmentName = studentDepartmentDepartmentName( student );
        if ( departmentName != null ) {
            student1.setDepartmentName( departmentName.name() );
        }
        GroupNumber groupValue = studentGroupGroupValue( student );
        if ( groupValue != null ) {
            student1.setGroup( groupValue.name() );
        }
        CourseNumber courseNumber = studentCourseCourseNumber( student );
        if ( courseNumber != null ) {
            student1.setCourse( courseNumber.name() );
        }
        student1.setId( student.getId() );
        student1.setFio( student.getFio() );
        student1.setPhoneNumber( student.getPhoneNumber() );

        return student1;
    }

    @Override
    public List<Student> studentEntityListToStudentList(List<StudentEntity> studentEntityList) {
        if ( studentEntityList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( studentEntityList.size() );
        for ( StudentEntity studentEntity : studentEntityList ) {
            list.add( studentEntityToStudent( studentEntity ) );
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
