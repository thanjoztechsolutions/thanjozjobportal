package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String role;

    private String email;

    private String phone;

    private String contractorId;

    private String username;

    private String adminId;

    private String password;
}