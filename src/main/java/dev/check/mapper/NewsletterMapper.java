package dev.check.mapper;

import dev.check.dto.Address;
import dev.check.dto.Newsletter;
import dev.check.entity.*;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import dev.check.entity.Auxiliary.AddressGroupEntity;
import dev.check.entity.EnumEntity.DepartmentName;
import dev.check.entity.EnumEntity.Role;
import dev.check.entity.EnumEntity.Status;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsletterMapper {

    @Mapping(target = "addresses", ignore = true)
    public NewsletterEntity newsletterToNewsletterEntity(Newsletter newsletter);

    @Named("mapRole")
    public Role mapRole(String role);

    @Named("mapDepartment")
    public DepartmentName mapDepartment(String departmentName);

    @Mappings({
            @Mapping(source = "address.role", target = "role", qualifiedByName = "mapRole"),
            @Mapping(source = "address.course", target = "course", defaultExpression = "java(null)"),
            @Mapping(source = "address.departments", target = "departments", defaultExpression = "java(null)"),
            @Mapping(source = "groups", target = "groups", defaultExpression = "java(null)"),
            @Mapping(target = "newsletter", ignore = true)
    })
    public AddressEntity addressToAddressEntity(Address address);

    @Mapping(target = ".", source = "departmentName")
    public String departmentToString(DepartmentEntity department);

    public List<String> departmentListToString (List<DepartmentEntity> departmentEntityList);

    @Mapping(target = "departmentName", source = ".")
    public DepartmentEntity StringToDepartment(String dep);

    List<DepartmentEntity> listStringDepartmentToListDep(List<String> dep);

    public Status stringToStatus(String status);

    public NewsletterEntity newsletterDtoToNewsletter(Newsletter newsletterDto);

    public Newsletter newsletterdEntityToNewsletter(NewsletterEntity entity);

    public List<Newsletter> newsletterEntityListToNewsletterList(List<NewsletterEntity> entities);

    public List<GroupEntity> map(List<String> value);

    public GroupEntity map(String value);

    public List<AddressDepartmentEntity> mapAddDepartmentList(List<String> value);
    public AddressDepartmentEntity mapAddDepartment(String value);

    List<AddressGroupEntity> mapList(List<String> value);

    AddressGroupEntity mapGroup(String value);

    public CourseEntity mapCourse(String value);
}
