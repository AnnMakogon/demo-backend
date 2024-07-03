package dev.check.repositories;

import dev.check.entity.Newsletter;
import dev.check.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends CrudRepository<Newsletter, Long>, PagingAndSortingRepository<Newsletter, Long> {
    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletter " +
            " WHERE CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring% OR date LIKE %:substring%" )
    List<Newsletter> getNewsletter (@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletter " +
            " WHERE (CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring% OR date LIKE %:substring%) AND mess = false" )
    List<Newsletter> getUnreadNl (@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = " SELECT COUNT(*) FROM newsletter " +
            " WHERE CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring% OR date LIKE %:substring%" )
    int getLengthStudents (@Param("substring") String substring);
}
