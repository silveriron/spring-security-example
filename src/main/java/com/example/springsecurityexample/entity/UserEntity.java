package com.example.springsecurityexample.entity;

import com.example.springsecurityexample.domain.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole role;

}
