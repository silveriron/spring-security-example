package com.example.springsecurityexample.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SignupRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
