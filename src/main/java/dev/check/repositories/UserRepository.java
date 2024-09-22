package dev.check.repositories;

import dev.check.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Users u")
    public List<UserEntity> getAllUsers();

    //для того, чтобы найти id у вошедшего студента [STUDENT]
    @Query(nativeQuery = true, value = "SELECT u.id FROM Users u WHERE u.username = :username")
    public Long findIdByName(@Param("username") String username);

    // чтобы проверить есть ли уже такой юзер
    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Users u WHERE u.username = :username")
    public boolean findUserByName(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT u.email " +
            "FROM users u " +
            "JOIN students s ON u.student_id = s.id " +
            "WHERE ((s.department_name IS NULL AND :department IS NULL) OR " +
            "(s.department_name = :department)) " +
            "AND (CAST s.course AS TEXT) = :course " +
            "AND (CAST s.group_of_students AS TEXT) = :group_of_student")
    public List<String> getNewsletterForSent(@Param("course") String course, @Param("department") String department,
                              @Param("group_of_student") String group);

}
