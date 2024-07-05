package dev.check.repositories;

import dev.check.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(nativeQuery = true, value = " SELECT * " +
                                        " FROM users")
    public List<User> getAllUsers();


}
