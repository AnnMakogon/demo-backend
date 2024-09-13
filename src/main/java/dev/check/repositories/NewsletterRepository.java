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
    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletters " +
            " WHERE CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%")
    public Page<NewsletterEntity> getNewsletter(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = " SELECT * " +
            " FROM newsletters " +
            " WHERE (CAST(id AS TEXT) LIKE %:substring% OR text LIKE %:substring% OR " +
            " subject LIKE %:substring%) AND sent = false")
    public Page<NewsletterEntity> getUnreadNl(@Param("substring") String substring, Pageable page);

    @Query(nativeQuery = true, value = "SELECT n.* FROM newsletters n WHERE CAST(status AS TEXT) = 'NOTSENT' OR status = 'INPROCESSING' ORDER BY n.date ASC LIMIT :size")
    public List<NewsletterEntity> getForSentScheduler(@Param("size") int size);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE newsletters SET status = :status WHERE id = :id")
    public void changeStatus(Long id, String status);

}
