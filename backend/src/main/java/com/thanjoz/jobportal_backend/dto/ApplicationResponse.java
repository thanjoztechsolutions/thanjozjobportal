package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationResponse {

    private Long id;

    private Long userId;

    private Long jobId;

    private String jobTitle;

    private String company;

    private String location;

    private ApplicationStatus status;

    // Applicant details
    private String name;

    private String email;

    private String phone;

    private String qualification;

    private String experience;

    private String resume;
}