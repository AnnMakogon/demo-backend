package dev.check.entity;

import dev.check.entity.EnumEntity.DepartmentName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "departments")
@SequenceGenerator(name = "dep_seq", sequenceName = "dep_seq", allocationSize = 1)
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dep_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DepartmentName departmentName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "departments", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    public DepartmentEntity(DepartmentName dep){
        this.departmentName = dep;
        this.students = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

}
