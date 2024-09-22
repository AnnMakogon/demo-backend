package dev.check.service;

import dev.check.dto.User;
import dev.check.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional  //из-за обращения в бд вынесено в сервис
    public User getUserForLogin(UsernamePasswordAuthenticationToken token){
        return new User(userRepository.findIdByName(token.getName()), token.getName(), String.valueOf(token.getAuthorities()), true );
    }
}
