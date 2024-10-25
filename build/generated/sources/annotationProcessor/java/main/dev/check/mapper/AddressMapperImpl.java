package dev.check.mapper;

import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-22T18:56:45+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public List<String> mapCourseList(List<AddressCourseEntity> value) {
        if ( value == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( value.size() );
        for ( AddressCourseEntity addressCourseEntity : value ) {
            list.add( mapCourse( addressCourseEntity ) );
        }

        return list;
    }

    @Override
    public String mapCourse(AddressCourseEntity value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }

    @Override
    public List<String> mapDepartmentList(List<AddressDepartmentEntity> value) {
        if ( value == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( value.size() );
        for ( AddressDepartmentEntity addressDepartmentEntity : value ) {
            list.add( mapDepartment( addressDepartmentEntity ) );
        }

        return list;
    }

    @Override
    public String mapDepartment(AddressDepartmentEntity value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }

    @Override
    public List<String> mapGroupList(List<AddressGroupEntity> value) {
        if ( value == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( value.size() );
        for ( AddressGroupEntity addressGroupEntity : value ) {
            list.add( mapGroup( addressGroupEntity ) );
        }

        return list;
    }

    @Override
    public String mapGroup(AddressGroupEntity value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }
}
