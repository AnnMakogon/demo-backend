package dev.check.repositories;

import dev.check.entity.Auxiliary.AddressCourseEntity;
import dev.check.entity.Auxiliary.AddressDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDepartmentRepository extends JpaRepository<AddressDepartmentEntity, Long> {
}
