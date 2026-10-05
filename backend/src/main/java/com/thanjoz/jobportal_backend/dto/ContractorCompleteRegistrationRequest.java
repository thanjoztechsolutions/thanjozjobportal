package com.thanjoz.jobportal_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContractorCompleteRegistrationRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String address;

    private String gst;

    private String companyLicense;

}