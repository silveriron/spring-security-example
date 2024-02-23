package com.example.springsecurityexample.service;

import com.example.springsecurityexample.adapter.JwtAdapter;
import com.example.springsecurityexample.domain.CustomUserDetails;
import com.example.springsecurityexample.domain.User;
import com.example.springsecurityexample.domain.UserRole;
import com.example.springsecurityexample.entity.UserEntity;
import com.example.springsecurityexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtAdapter jwtAdapter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User signup(String email, String password) {

        findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입된 유저입니다.");
                });

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                        .role(UserRole.USER)
                .build());

        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }

    public String login(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(email, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();

        String token = jwtAdapter.createToken(userDetails.getUser().getEmail());

        return token;

    }
}
