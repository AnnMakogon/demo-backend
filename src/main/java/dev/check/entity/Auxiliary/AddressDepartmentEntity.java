package dev.check.entity.Auxiliary;

import dev.check.entity.AddressEntity;
import dev.check.entity.DepartmentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "addresses_departments")
@SequenceGenerator(name = "addDep_seq", sequenceName = "addDep_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addDep_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

}
