package com.example.springsecurityexample.filter;

import com.example.springsecurityexample.adapter.JwtAdapter;
import com.example.springsecurityexample.domain.CustomUserDetails;
import com.example.springsecurityexample.domain.User;
import com.example.springsecurityexample.entity.UserEntity;
import com.example.springsecurityexample.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtAdapter jwtAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null) {

            String email = jwtAdapter.getEmail(token);

            UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

            User user = User.builder()
                    .id(userEntity.getId())
                    .email(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRole())
                    .build();

            UserDetails userDetails = new CustomUserDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
