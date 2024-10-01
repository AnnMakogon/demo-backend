package dev.check.mapper;

import dev.check.entity.EnumEntity.Role;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    Role stringToRole(String role);

}