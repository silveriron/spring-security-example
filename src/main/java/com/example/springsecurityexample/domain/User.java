package com.example.springsecurityexample.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
}
