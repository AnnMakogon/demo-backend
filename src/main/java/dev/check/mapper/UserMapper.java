package dev.check.mapper;

import dev.check.DTO.StudentRegistrDTO;
import dev.check.entity.Password;
import dev.check.entity.Role;
import dev.check.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    Password map(String value);

    Role role = Role.STUDENT;

    public Role stringToRole(String role);

    boolean enable = true;

    @Mappings({
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "username", source = "studentDtoAuth.fio"),
            @Mapping(target = "enable", source = "enable"),
            @Mapping(target = "password", expression = "java(map(studentDtoAuth.getPassword_id()))")
    })
    public User studentDtoAuthToUser(StudentRegistrDTO studentDtoAuth);
}