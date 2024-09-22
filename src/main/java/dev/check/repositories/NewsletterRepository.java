package dev.check.repositories;

import dev.check.entity.NewsletterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<NewsletterEntity, Long> {
    @Query(nativeQuery = true, value = " SELECT n.*, a.* " +
            " FROM newsletters n " +
            " LEFT JOIN addresses a ON n.id = a.newsletter_id " +
            " WHERE CAST(n.id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%")
    public Page<NewsletterEntity> getNewsletter(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = " SELECT n.*, a.* " +
            " FROM newsletters n " +
            " LEFT JOIN addresses a ON n.id = a.newsletter_id " +
            " WHERE (CAST(n.id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%) AND sent = false")
    public Page<NewsletterEntity> getUnreadNewsletter(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = "SELECT n.*, a.* " +
            " FROM newsletters n " +
            " LEFT JOIN addresses a ON n.id = a.newsletter_id " +
            " WHERE CAST(status AS TEXT) = 'NOTSENT' OR status = 'INPROCESSING' " +
            " ORDER BY n.date ASC LIMIT :size")
    public List<NewsletterEntity> getForSentScheduler(@Param("size") int size);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE newsletters SET status = :status WHERE id = :id")
    public void changeStatus(Long id, String status);

    //Без нативного sql
    /*@Query(value = "SELECT n FROM NewsletterEntity n " +
            "JOIN FETCH n.addresses a " +
            "JOIN FETCH a.groups ag " +
            "JOIN FETCH a.departments ad " +
            "WHERE n.id = :id")
    NewsletterEntity findNewsletterWithAddressesById(@Param("id") Long id);*/

    //С нативным sql
    /*@Query(nativeQuery = true, value = "SELECT n.id, n.date, n.text, n.subject, n.sent, n.status, " +
            "a.id AS address_id, a.role, a.course_id, ag.id AS address_group_id, ag.group_id, " +
            "ad.id AS address_department_id, ad.department_id " +
            "FROM newsletters n " +
            "LEFT JOIN addresses a ON n.id = a.newsletter_id " +
            "LEFT JOIN address_group ag ON a.id = ag.address_id " +
            "LEFT JOIN address_department ad ON a.id = ad.address_id " +
            "WHERE n.id = :id")
    NewsletterEntity findNewsletterWithAddressesById(@Param("id") Long id);*/
}
