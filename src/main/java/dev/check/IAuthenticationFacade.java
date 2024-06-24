package dev.check;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    UsernamePasswordAuthenticationToken getAuthentication();
}
