package dev.check.repositories;

import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDepartmentRepository extends JpaRepository<AddressDepartmentEntity, Long> {
}
