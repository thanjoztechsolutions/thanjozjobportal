package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String role;

    // Applicant login
    private String email;
    private String phone;

    // Contractor login
    // Contractor now logs in using email + password

    // Super Admin login
    // Uses email + password

    private String password;
}