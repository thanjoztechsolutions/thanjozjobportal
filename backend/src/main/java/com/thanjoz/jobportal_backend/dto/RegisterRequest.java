package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

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

    private String phone;

    private String address;

    private String gst;

    private String companyLicense;

    private String resume;
}