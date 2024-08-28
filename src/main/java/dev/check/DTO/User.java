package dev.check.DTO;

import dev.check.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String role;
    private boolean enable;

    public User(Principal principal, UserRepository userRepository1){
        UsernamePasswordAuthenticationToken userData = (UsernamePasswordAuthenticationToken) principal;
        this.id = userRepository1.findIdByName(userData.getName());
        this.username = userData.getName();
        this.role =  String.valueOf(userData.getAuthorities());
        this.enable = true;
    }

}
