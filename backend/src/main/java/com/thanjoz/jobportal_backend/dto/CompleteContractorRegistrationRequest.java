package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class CompleteContractorRegistrationRequest {

    private String contractorId;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private String phone;

    private String companyName;

    private String companyLicense;

    private String address;

    private String gst;

    private String location;

    private String qualification;

    private String experience;
}