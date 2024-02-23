package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.controller.dto.LoginRequest;
import com.example.springsecurityexample.controller.dto.SignupRequest;
import com.example.springsecurityexample.domain.CustomUserDetails;
import com.example.springsecurityexample.domain.User;
import com.example.springsecurityexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @RequestBody SignupRequest signupRequest
    ) {
        User user = userService.signup(signupRequest.getEmail(), signupRequest.getPassword());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request) {

        String token = userService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        User user = userDetails.getUser();

        return ResponseEntity.ok(user);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

}
