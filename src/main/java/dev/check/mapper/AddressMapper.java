package dev.check.mapper;

import dev.check.dto.Address;
import dev.check.entity.AddressEntity;
import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.Role;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    List<String> mapCourseList(List<AddressCourseEntity> value);

    String mapCourse(AddressCourseEntity value);

    List<String> mapDepartmentList(List<AddressDepartmentEntity> value);

    String mapDepartment(AddressDepartmentEntity value);

    List<String> mapGroupList(List<AddressGroupEntity> value);

    String mapGroup(AddressGroupEntity value);
}
