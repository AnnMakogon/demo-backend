package dev.check.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "passwords")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "pas_seq", sequenceName = "pas_seq", allocationSize = 1)
public class Password {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Password(String password) {
        this.password = passwordEncoder.encode(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pas_seq")
    private Long id;

    @Column(name="password")
    private String password;

    @JsonIgnore
    private void setPasswordWithEncoding(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
