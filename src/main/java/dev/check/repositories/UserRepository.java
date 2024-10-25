package dev.check.repositories;

import dev.check.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Users u")
    List<UserEntity> getAllUsers();

    //для того, чтобы найти id у вошедшего студента [STUDENT]
    @Query(nativeQuery = true, value = "SELECT u.id FROM Users u WHERE u.username = :username")
    Long findIdByName(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT u.student_id FROM Users u WHERE u.username = :username")
    Long findStudentIdByName(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT u.enable_Email FROM Users u WHERE u.username = :username")
    boolean findEnableEmailByName(@Param("username") String username);

    // чтобы проверить есть ли уже такой юзер
    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Users u WHERE u.username = :username")
    boolean findUserByName(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT u.email " +
           "FROM users u " +
           "JOIN students s ON u.student_id = s.id " +
           "JOIN groups g ON s.group_id = g.id " +
           "JOIN departments d ON s.department_id = d.id " +
           "JOIN courses c ON s.course_id = c.id " +
           "WHERE ((d.department_name IS NULL AND :department IS NULL) OR " +
           "(d.department_name = :department)) " +
           "AND (c.course_number = :course) " +
           "AND (g.group_value = :group_of_student)")
    List<String> getNewsletterForSent(@Param("course") String course,
                                            @Param("department") String department,
                                            @Param("group_of_student") String group);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET enable_email = true WHERE id = :id")
    void confirmation(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT u.* FROM users u WHERE u.username = :username")
    UserEntity getUserByName(@Param("username") String username);
}
