package dev.check.controller;

import dev.check.DTO.UserDto;
import dev.check.IAuthenticationFacade;
import dev.check.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
@NoArgsConstructor
public class AuthorizationController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PostMapping(value = "/login")
    public void apiLogin(Principal user) {         // todo почитать про собственные токены
        log.info("Login user");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        log.info("Hello {} with role {}", token.getName(), token.getAuthorities());
    }

   @PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(
                AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
        );
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);   //todo переделать, чтоб если не залогиненное 401
                                                                                    // - переправлял на логин и смотреть откуда идут запросы,
                                                                                    //   т.е. не делать запросы до того, как пройдет аутентификацию,
                                                                                    //   почитать, про куки на java как их обновлять

        return user;
    }

    @GetMapping(path = "/persUser")
    public UserDto getpersUser(){
        UsernamePasswordAuthenticationToken userData = authenticationFacade.getAuthentication(); // получаем пользователя
        return  new UserDto(null, userData.getName(), String.valueOf(userData.getAuthorities()), true );
    }

}