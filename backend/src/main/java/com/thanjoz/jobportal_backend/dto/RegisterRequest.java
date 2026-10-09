package com.thanjoz.jobportal_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;


@Data
public class RegisterRequest {

    private String role;

    private String firstName;

    private String lastName;

    private String contractorId;

    private String companyName;

    private String username;

    private String password;

    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phone;

    private String address;

    private String gst;

    private String companyLicense;

    private String resume;
}