package dev.check.repositories;

import dev.check.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    @Query(nativeQuery = true, value =  " SELECT * " +
                                        " FROM students " +
                                        " WHERE CAST(id AS TEXT) LIKE %:substring% OR fio LIKE %:substring% OR " +
                                        " group_of_students LIKE %:substring% OR phone_number LIKE %:substring%")
    List<Student> getStudentsAdmin (@Param("substring") String substring, Pageable page);  // это запрос на полные данные

    @Query(nativeQuery = true, value = "SELECT id, fio, group_of_students, " +
            "CASE WHEN fio = :userName THEN phone_number ELSE ' ' END as phone_number " +
            "FROM students " +
            "WHERE CAST(id AS TEXT) LIKE %:substring% OR fio LIKE %:substring% OR " +
            "group_of_students LIKE %:substring% OR phone_number LIKE %:substring%")
    List<Student> getStudentsStudent(@Param("substring") String substring, Pageable page, @Param("userName") String userName);

    @Query(nativeQuery = true, value = " SELECT COUNT(*) FROM students "  +
            " WHERE CAST(id AS TEXT) LIKE %:substring% OR fio LIKE %:substring% OR " +
            " group_of_students LIKE %:substring% OR phone_number LIKE %:substring%")
    int getLengthStudents (@Param("substring") String substring);
}
