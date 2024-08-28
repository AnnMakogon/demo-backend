package dev.check.repositories;

import dev.check.entity.NewsletterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<NewsletterEntity, Long> {
    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletters " +
            " WHERE CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%")
    Page<NewsletterEntity> getNewsletter(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletters " +
            " WHERE (CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%) AND sent = false")
    Page<NewsletterEntity> getUnreadNl(@Param("substring") String substring, Pageable page);

    //для отправки
    @Query(nativeQuery = true, value = "SELECT * FROM newsletters WHERE status = 'NOTSENT' OR status = 'INPROCESSING' ")
    Page<NewsletterEntity> getNlForDeparture(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT u.email " +
            "FROM users u " +
            "JOIN students s ON u.student_id = s.id " +
            "WHERE ((s.department_name IS NULL AND :department IS NULL) OR " +
            "(s.department_name = :department)) " +
            "AND s.course = :course " +
            "AND s.group_of_students = :group_of_student")
    List<String> getNlForSent(@Param("course") Integer course, @Param("department") String department,
                              @Param("group_of_student") Float group);

    @Query(nativeQuery = true, value = "SELECT email FROM users WHERE CAST(role AS TEXT) = 'ADMIN'")
    List<String> getAdminForSent();

    @Query(nativeQuery = true, value = "SELECT n.* FROM newsletters n WHERE CAST(status AS TEXT) = 'NOTSENT' LIMIT 10")
    List<NewsletterEntity> getForSentScheduler();
}
