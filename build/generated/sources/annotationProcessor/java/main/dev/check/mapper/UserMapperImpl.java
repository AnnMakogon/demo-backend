package dev.check.mapper;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.entity.Password;
import dev.check.entity.Role;
import dev.check.entity.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T12:47:52+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Password map(String value) {
        if ( value == null ) {
            return null;
        }

        Password password = new Password();

        return password;
    }

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

    @Override
    public User studentDtoAuthToUser(StudentRegistrDTO studentDtoAuth) {
        if ( studentDtoAuth == null ) {
            return null;
        }

        User user = new User();

        user.setRole( stringToRole( studentDtoAuth.getRole() ) );
        user.setUsername( studentDtoAuth.getFio() );
        user.setEnable( studentDtoAuth.isEnable() );
        user.setId( studentDtoAuth.getId() );
        user.setEmail( studentDtoAuth.getEmail() );

        user.setPassword( map(studentDtoAuth.getPassword_id()) );

        return user;
    }
}
