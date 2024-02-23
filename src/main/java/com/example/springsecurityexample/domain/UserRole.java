package com.example.springsecurityexample.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String value;
}
