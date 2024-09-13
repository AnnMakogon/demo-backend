package dev.check.mapper;

import dev.check.DTO.Address;
import dev.check.DTO.Newsletter;
import dev.check.entity.AddressEntity;
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
    date = "2024-09-13T16:02:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class NlMapperImpl implements NlMapper {

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
    public AddressEntity addressToAddressEntity(Address address, String group) {
        if ( address == null && group == null ) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();

        if ( address != null ) {
            addressEntity.setRole( mapRole( address.getRole() ) );
            if ( address.getCourse() != null ) {
                addressEntity.setCourse( Integer.parseInt( address.getCourse() ) );
            }
            else {
                addressEntity.setCourse( null );
            }
            if ( address.getDepartment() != null ) {
                addressEntity.setDepartment( mapDepartment( address.getDepartment() ) );
            }
            else {
                addressEntity.setDepartment( null );
            }
        }
        if ( group != null ) {
            addressEntity.setGroup( Float.parseFloat( group ) );
        }
        else {
            addressEntity.setGroup( null );
        }

        return addressEntity;
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
    public NewsletterEntity newsletterDtoToNewsletter(Newsletter nlDto) {
        if ( nlDto == null ) {
            return null;
        }

        NewsletterEntity newsletterEntity = new NewsletterEntity();

        newsletterEntity.setId( nlDto.getId() );
        newsletterEntity.setDate( nlDto.getDate() );
        newsletterEntity.setText( nlDto.getText() );
        newsletterEntity.setSubject( nlDto.getSubject() );
        newsletterEntity.setSent( nlDto.getSent() );
        newsletterEntity.setStatus( stringToStatus( nlDto.getStatus() ) );

        return newsletterEntity;
    }

    @Override
    public Newsletter newsletterdEntityToNewsletter(NewsletterEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Newsletter newsletter = new Newsletter();

        newsletter.setId( entity.getId() );
        newsletter.setDate( entity.getDate() );
        newsletter.setText( entity.getText() );
        newsletter.setSubject( entity.getSubject() );
        newsletter.setSent( entity.getSent() );
        if ( entity.getStatus() != null ) {
            newsletter.setStatus( entity.getStatus().name() );
        }

        return newsletter;
    }

    @Override
    public List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Newsletter> list = new ArrayList<Newsletter>( entities.size() );
        for ( NewsletterEntity newsletterEntity : entities ) {
            list.add( newsletterdEntityToNewsletter( newsletterEntity ) );
        }

        return list;
    }
}
