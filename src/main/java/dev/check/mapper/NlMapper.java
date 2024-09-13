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
    public NewsletterEntity newsletterToNewsletterEntity(Newsletter newsletter);

    @Named("mapRole")
    public Role mapRole(String role);

    @Named("mapDepartment")
    public DepartmentName mapDepartment(String departmentName);

    @Mappings({
            @Mapping(source = "address.role", target = "role", qualifiedByName = "mapRole"),
            @Mapping(source = "address.course", target = "course", defaultExpression = "java(null)"),
            @Mapping(source = "address.department", target = "department", qualifiedByName = "mapDepartment", defaultExpression = "java(null)"),
            @Mapping(source = "group", target = "group", defaultExpression = "java(null)"),
            @Mapping(target = "newsletter", ignore = true)
    })
    public AddressEntity addressToAddressEntity(Address address, String group);

    public Status stringToStatus(String status);

    public NewsletterEntity newsletterDtoToNewsletter(Newsletter nlDto);

    public Newsletter newsletterdEntityToNewsletter(NewsletterEntity entity);

    public List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities);

}
