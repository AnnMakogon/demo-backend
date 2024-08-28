package dev.check.repositories;

import dev.check.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long>, PagingAndSortingRepository<StudentEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM students " +
            "WHERE CAST(id AS TEXT) LIKE %:substring% " +
            "OR fio LIKE %:substring% " +
            "OR CAST(group_of_students AS TEXT) LIKE %:substring% " +
            "OR phone_number LIKE %:substring% " +
            "OR CAST(course AS TEXT) LIKE %:substring% " +
            "OR department_name LIKE %:substring% ")
    Page<StudentEntity> getStudentsAdmin(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = "SELECT id, fio, group_of_students, " +
            "CASE WHEN fio = :userName THEN phone_number ELSE ' ' END as phone_number " +
            "FROM students " +
            "WHERE CAST(id AS TEXT) LIKE %:substring% OR fio LIKE %:substring% OR " +
            "group_of_students LIKE %:substring% OR phone_number LIKE %:substring%")
    Page<StudentEntity> getStudentsStudent(@Param("substring") String substring, Pageable page, @Param("userName") String userName);
}
