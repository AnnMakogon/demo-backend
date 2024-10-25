package dev.check.service;

import dev.check.dto.User;
import dev.check.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional  //из-за обращения в бд вынесено в сервис
    public User getUserForLogin(UsernamePasswordAuthenticationToken token){
        return new User(userRepository.findIdByName(token.getName()), token.getName(),
                String.valueOf(token.getAuthorities()), true,
                userRepository.findEnableEmailByName(token.getName()), userRepository.findStudentIdByName(token.getName()) );
    }
}
