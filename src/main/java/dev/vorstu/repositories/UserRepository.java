package dev.vorstu.repositories;

import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {


}
