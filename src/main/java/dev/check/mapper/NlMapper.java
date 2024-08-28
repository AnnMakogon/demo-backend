package dev.check.mapper;

import dev.check.DTO.Address;
import dev.check.DTO.Newsletter;
import dev.check.entity.*;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import org.mapstruct.*;
import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NlMapper {

    @Mapping(target = "addresses", ignore = true)
    NewsletterEntity newsletterToNewsletterEntity(Newsletter newsletter);

    @Named("mapRole")
    Role mapRole(String role);

    @Named("mapDepartment")
    DepartmentName mapDepartment(String departmentName);

    @Mappings({
            @Mapping(source = "address.role", target = "role", qualifiedByName = "mapRole"),
            @Mapping(source = "address.course", target = "course", defaultExpression = "java(null)"),
            @Mapping(source = "address.department", target = "department", qualifiedByName = "mapDepartment", defaultExpression = "java(null)"),
            @Mapping(source = "group", target = "group", defaultExpression = "java(null)"),
            @Mapping(target = "newsletter", ignore = true)
    })
    AddressEntity addressToAddressEntity(Address address, String group);

    public Status stringToStatus(String status);
    NewsletterEntity newsletterDtoToNewsletter(Newsletter nlDto);

    Newsletter newsletterdEntityToNewsletter(NewsletterEntity entity);

    List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities);

}
