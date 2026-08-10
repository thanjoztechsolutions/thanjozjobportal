package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.LoginRequest;
import com.thanjoz.jobportal_backend.dto.RegisterRequest;
import com.thanjoz.jobportal_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thanjoz.jobportal_backend.dto.LoginResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody RegisterRequest request) {

        String result = userService.registerUser(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @RequestBody LoginRequest request) {

        LoginResponse result = userService.loginUser(request);

        return ResponseEntity.ok(result);
    }
}