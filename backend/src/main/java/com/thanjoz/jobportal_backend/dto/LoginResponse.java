package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {

    private String token;

    private Long userId;

    private String contractorId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String qualification;

    private String experience;

    private String address;

    private String gst;

    private String companyName;

    private String companyLicense;

    private String location;

    private String status;

    private LocalDateTime joined;

    private String role;


    public LoginResponse(
            String token,
            User user) {

        this.token = token;

        this.userId = user.getId();

        this.contractorId = user.getContractorId();

        this.firstName = user.getFirstName();

        this.lastName = user.getLastName();

        this.email = user.getEmail();

        this.phone = user.getPhone();

        this.qualification = user.getQualification();

        this.experience = user.getExperience();

        this.address = user.getAddress();

        this.gst = user.getGst();

        this.companyName = user.getCompanyName();

        this.companyLicense = user.getCompanyLicense();

        this.location = user.getLocation();

        this.status = user.getStatus();

        this.joined = user.getJoined();

        this.role =
                user.getRole() != null
                        ? user.getRole().name()
                        : null;
    }
}