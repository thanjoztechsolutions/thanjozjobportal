package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.LoginRequest;
import com.thanjoz.jobportal_backend.dto.RegisterRequest;
import com.thanjoz.jobportal_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thanjoz.jobportal_backend.dto.LoginResponse;
import com.thanjoz.jobportal_backend.dto.RegisterResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse result = userService.registerUser(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @RequestBody LoginRequest request) {

        LoginResponse result = userService.loginUser(request);

        return ResponseEntity.ok(result);
    }
}