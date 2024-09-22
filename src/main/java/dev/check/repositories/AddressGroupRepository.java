package dev.check.repositories;

import dev.check.entity.Auxiliary.AddressGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressGroupRepository extends JpaRepository<AddressGroupEntity, Long> {
}
