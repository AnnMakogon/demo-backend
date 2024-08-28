package dev.check.config;

import dev.check.entity.EnumEntity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/login/**", "/api/base/registration/**",
                        "/api/base/proof/**", "/api/registr/**",
                        "**/newsletter/**", "/gs-guide-websocket/**").permitAll()
                //For STUDENT (table)
                .antMatchers("/api/base/students/**").hasAnyAuthority(Role.STUDENT.name(), Role.ADMIN.name())

                .antMatchers("/api/mail/newsletter/**").hasAuthority(Role.ADMIN.name())

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                })
                .and()
                .csrf().disable()
                .cors().disable();

        return http.build();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery(
                        "select username, p.password as Passwords, enable "
                                + "from users as u "
                                + "inner join Passwords as p on u.password_id = p.id "
                                + "where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }
}
