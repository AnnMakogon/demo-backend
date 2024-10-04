package dev.check.entity.Auxiliary;

import dev.check.entity.AddressEntity;
import dev.check.entity.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "addresses_groups")
@SequenceGenerator(name = "addGr_seq", sequenceName = "addGr_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class AddressGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addGr_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

}
