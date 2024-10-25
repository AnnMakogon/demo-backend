package dev.check.mapper;

import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.CourseEntity;
import dev.check.entity.DepartmentEntity;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import dev.check.entity.NewsletterEntity;
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
public class NewsletterMapperImpl implements NewsletterMapper {

    @Override
    public NewsletterEntity newsletterToNewsletterEntity(Newsletter newsletter) {
        if ( newsletter == null ) {
            return null;
        }

        NewsletterEntity newsletterEntity = new NewsletterEntity();

        newsletterEntity.setId( newsletter.getId() );
        newsletterEntity.setDate( newsletter.getDate() );
        newsletterEntity.setText( newsletter.getText() );
        newsletterEntity.setSubject( newsletter.getSubject() );
        newsletterEntity.setSent( newsletter.getSent() );
        newsletterEntity.setStatus( stringToStatus( newsletter.getStatus() ) );

        return newsletterEntity;
    }

    @Override
    public Role mapRole(String role) {
        if ( role == null ) {
            return null;
        }

        Role role1;

        switch ( role ) {
            case "STUDENT": role1 = Role.STUDENT;
            break;
            case "ADMIN": role1 = Role.ADMIN;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + role );
        }

        return role1;
    }

    @Override
    public DepartmentName mapDepartment(String departmentName) {
        if ( departmentName == null ) {
            return null;
        }

        DepartmentName departmentName1;

        switch ( departmentName ) {
            case "KFA": departmentName1 = DepartmentName.KFA;
            break;
            case "KMA": departmentName1 = DepartmentName.KMA;
            break;
            case "KUCP": departmentName1 = DepartmentName.KUCP;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + departmentName );
        }

        return departmentName1;
    }

    @Override
    public AddressEntity addressToAddressEntity(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setRole( mapRole( address.getRole() ) );
        addressEntity.setCourses( mapCourseList( address.getCourses() ) );
        addressEntity.setDepartments( mapAddDepartmentList( address.getDepartments() ) );
        addressEntity.setGroups( mapList( address.getGroups() ) );

        return addressEntity;
    }

    @Override
    public String departmentToString(DepartmentEntity department) {
        if ( department == null ) {
            return null;
        }

        String string = new String();

        return string;
    }

    @Override
    public List<String> departmentListToString(List<DepartmentEntity> departmentEntityList) {
        if ( departmentEntityList == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( departmentEntityList.size() );
        for ( DepartmentEntity departmentEntity : departmentEntityList ) {
            list.add( departmentToString( departmentEntity ) );
        }

        return list;
    }

    @Override
    public DepartmentEntity StringToDepartment(String dep) {
        if ( dep == null ) {
            return null;
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();

        if ( dep != null ) {
            departmentEntity.setDepartmentName( Enum.valueOf( DepartmentName.class, dep ) );
        }

        return departmentEntity;
    }

    @Override
    public List<DepartmentEntity> listStringDepartmentToListDep(List<String> dep) {
        if ( dep == null ) {
            return null;
        }

        List<DepartmentEntity> list = new ArrayList<DepartmentEntity>( dep.size() );
        for ( String string : dep ) {
            list.add( StringToDepartment( string ) );
        }

        return list;
    }

    @Override
    public Status stringToStatus(String status) {
        if ( status == null ) {
            return null;
        }

        Status status1;

        switch ( status ) {
            case "INPROCESSING": status1 = Status.INPROCESSING;
            break;
            case "ERROR": status1 = Status.ERROR;
            break;
            case "SUCCESSFULLY": status1 = Status.SUCCESSFULLY;
            break;
            case "NOTSENT": status1 = Status.NOTSENT;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return status1;
    }

    @Override
    public NewsletterEntity newsletterDtoToNewsletter(Newsletter newsletterDto) {
        if ( newsletterDto == null ) {
            return null;
        }

        NewsletterEntity newsletterEntity = new NewsletterEntity();

        newsletterEntity.setId( newsletterDto.getId() );
        newsletterEntity.setDate( newsletterDto.getDate() );
        newsletterEntity.setText( newsletterDto.getText() );
        newsletterEntity.setSubject( newsletterDto.getSubject() );
        newsletterEntity.setSent( newsletterDto.getSent() );
        newsletterEntity.setStatus( stringToStatus( newsletterDto.getStatus() ) );

        return newsletterEntity;
    }

    @Override
    public Newsletter newsletterEntityToNewsletter(NewsletterEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Newsletter newsletter = new Newsletter();

        newsletter.setAddress( addressEntitiesToAddresses( entity.getAddresses() ) );
        if ( entity.getStatus() != null ) {
            newsletter.setStatus( entity.getStatus().name() );
        }
        newsletter.setId( entity.getId() );
        newsletter.setDate( entity.getDate() );
        newsletter.setText( entity.getText() );
        newsletter.setSubject( entity.getSubject() );
        newsletter.setSent( entity.getSent() );

        return newsletter;
    }

    @Override
    public List<Address> addressEntitiesToAddresses(List<AddressEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( entities.size() );
        for ( AddressEntity addressEntity : entities ) {
            list.add( addressEntityToAddress( addressEntity ) );
        }

        return list;
    }

    @Override
    public Address addressEntityToAddress(AddressEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Address address = new Address();

        if ( entity.getRole() != null ) {
            address.setRole( entity.getRole().name() );
        }
        address.setCourses( mapCourses( entity.getCourses() ) );
        address.setGroups( mapGroups( entity.getGroups() ) );
        address.setDepartments( mapDepartments( entity.getDepartments() ) );

        return address;
    }

    @Override
    public List<String> mapCourses(List<AddressCourseEntity> value) {
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
    public List<String> mapGroups(List<AddressGroupEntity> value) {
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
    public List<String> mapDepartments(List<AddressDepartmentEntity> value) {
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
    public List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Newsletter> list = new ArrayList<Newsletter>( entities.size() );
        for ( NewsletterEntity newsletterEntity : entities ) {
            list.add( newsletterEntityToNewsletter( newsletterEntity ) );
        }

        return list;
    }

    @Override
    public List<AddressDepartmentEntity> mapAddDepartmentList(List<String> value) {
        if ( value == null ) {
            return null;
        }

        List<AddressDepartmentEntity> list = new ArrayList<AddressDepartmentEntity>( value.size() );
        for ( String string : value ) {
            list.add( mapAddDepartment( string ) );
        }

        return list;
    }

    @Override
    public AddressDepartmentEntity mapAddDepartment(String value) {
        if ( value == null ) {
            return null;
        }

        AddressDepartmentEntity addressDepartmentEntity = new AddressDepartmentEntity();

        return addressDepartmentEntity;
    }

    @Override
    public List<AddressGroupEntity> mapList(List<String> value) {
        if ( value == null ) {
            return null;
        }

        List<AddressGroupEntity> list = new ArrayList<AddressGroupEntity>( value.size() );
        for ( String string : value ) {
            list.add( mapGroup( string ) );
        }

        return list;
    }

    @Override
    public AddressGroupEntity mapGroup(String value) {
        if ( value == null ) {
            return null;
        }

        AddressGroupEntity addressGroupEntity = new AddressGroupEntity();

        return addressGroupEntity;
    }

    @Override
    public List<AddressCourseEntity> mapCourseList(List<String> value) {
        if ( value == null ) {
            return null;
        }

        List<AddressCourseEntity> list = new ArrayList<AddressCourseEntity>( value.size() );
        for ( String string : value ) {
            list.add( mapAddressCourse( string ) );
        }

        return list;
    }

    @Override
    public AddressCourseEntity mapAddressCourse(String course) {
        if ( course == null ) {
            return null;
        }

        AddressCourseEntity addressCourseEntity = new AddressCourseEntity();

        addressCourseEntity.setCourse( mapCourse( course ) );

        return addressCourseEntity;
    }

    @Override
    public CourseEntity mapCourse(String value) {
        if ( value == null ) {
            return null;
        }

        CourseEntity courseEntity = new CourseEntity();

        return courseEntity;
    }
}
