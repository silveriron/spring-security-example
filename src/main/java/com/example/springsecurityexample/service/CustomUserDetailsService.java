package com.example.springsecurityexample.service;

import com.example.springsecurityexample.domain.CustomUserDetails;
import com.example.springsecurityexample.domain.User;
import com.example.springsecurityexample.entity.UserEntity;
import com.example.springsecurityexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> existUser = userRepository.findByEmail(email);

        if (existUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = User.builder()
                .id(existUser.get().getId())
                .email(existUser.get().getEmail())
                .password(existUser.get().getPassword())
                .role(existUser.get().getRole())
                .build();

        return new CustomUserDetails(user);
    }
}
