package dev.check.mapper;

import dev.check.dto.User;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.StudentEntity;
import dev.check.entity.UserEntity;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-22T18:54:22+0300",
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

    @Override
    public User userEntityToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        user.setStudentId( userEntityStudentId( userEntity ) );
        user.setId( userEntity.getId() );
        user.setUsername( userEntity.getUsername() );
        if ( userEntity.getRole() != null ) {
            user.setRole( userEntity.getRole().name() );
        }
        user.setEnable( userEntity.isEnable() );
        user.setEnableEmail( userEntity.isEnableEmail() );

        return user;
    }

    private Long userEntityStudentId(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        StudentEntity student = userEntity.getStudent();
        if ( student == null ) {
            return null;
        }
        Long id = student.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
