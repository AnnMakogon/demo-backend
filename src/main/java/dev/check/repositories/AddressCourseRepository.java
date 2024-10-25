package dev.check.repositories;

import dev.check.entity.Auxiliary.AddressCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressCourseRepository extends JpaRepository<AddressCourseEntity, Long> {
}
