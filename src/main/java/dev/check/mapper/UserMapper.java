package dev.check.mapper;

import dev.check.dto.User;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    Role stringToRole(String role);

    @Mapping(source = "userEntity.student.id", target = "studentId"  )
    User userEntityToUserDto(UserEntity userEntity);

}