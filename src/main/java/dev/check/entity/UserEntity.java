package dev.check.entity;

import dev.check.entity.EnumEntity.Role;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class UserEntity {

    public UserEntity(String username, Role role, PasswordEntity password, boolean enable, String email) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.enable = enable;
        this.email = email;
    }

    public UserEntity(String username, Role role, PasswordEntity password, boolean enable, String email, StudentEntity student) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.enable = enable;
        this.email = email;
        this.student = student;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    private PasswordEntity password;

    private boolean enable;

    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

}
