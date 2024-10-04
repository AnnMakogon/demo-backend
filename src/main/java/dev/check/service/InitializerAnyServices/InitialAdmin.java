package dev.check.service.InitializerAnyServices;

import dev.check.entity.EnumEntity.Role;
import dev.check.entity.PasswordEntity;
import dev.check.entity.UserEntity;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitialAdmin {
    private UserRepository userRepository;

    public void initialAdmin(){
        ///////    ADMIN без связи со студентом, логично //////
        UserEntity admin = new UserEntity(
                "Admin",
                Role.ADMIN,
                new PasswordEntity("1234"),
                true,
                "myworkemail033@mail.ru"
        );
        userRepository.save(admin);
    }
}
