package dev.check.mapper;

import dev.check.entity.EnumEntity.Role;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-01T11:11:01+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Role stringToRole(String role) {
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
}
