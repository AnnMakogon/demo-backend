package dev.check.repositories;

import dev.check.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT s.*, g.*, d.* " +
            "FROM students s " +
            "LEFT JOIN groups g ON g.id = s.group_id " +
            "LEFT JOIN departments d ON d.id = s.department_id " +
            "WHERE (CAST(s.id AS TEXT) ILIKE %:substring% " +
            "OR s.fio ILIKE %:substring% " +
            "OR CAST(g.id AS TEXT) ILIKE %:substring% " +
            "OR s.phone_number ILIKE %:substring% " +
            "OR CAST(s.course_id AS TEXT) ILIKE %:substring% " +
            "OR CAST(d.department_name AS TEXT) ILIKE %:substring% " +
            "OR CAST(g.group_value AS TEXT) ILIKE %:substring%)")
    public Page<StudentEntity> getStudentsAdmin(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = "SELECT s.id, s.fio , g.*, d.* " +
            "CASE WHEN fio = :userName THEN phone_number ELSE ' ' END as phone_number " +
            "FROM students s " +
            "LEFT JOIN groups g ON g.id = s.group_id " +
            "LEFT JOIN departments d ON d.id = s.department_id " +
            "WHERE (CAST(s.id AS TEXT) ILIKE %:substring% " +
            "OR s.fio ILIKE %:substring% " +
            "OR CAST(g.id AS TEXT) ILIKE %:substring% " +
            "OR CAST(s.course AS TEXT) ILIKE %:substring% " +
            "OR CAST(d.department_name AS TEXT) ILIKE %:substring% " +
            "OR CAST(g.group_value AS TEXT) ILIKE %:substring%)")
    public Page<StudentEntity> getStudentsStudent(@Param("substring") String substring, Pageable page, @Param("userName") String userName);
}
