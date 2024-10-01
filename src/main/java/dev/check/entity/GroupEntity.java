package dev.check.entity;

import dev.check.entity.EnumEntity.GroupNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
@SequenceGenerator(name = "group_seq", sequenceName = "group_seq", allocationSize = 1)
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private GroupNumber groupValue;

    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;*/

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    public GroupEntity(GroupNumber groupValue){
        this.groupValue = groupValue;
        this.students = new ArrayList<>();
    }
}
