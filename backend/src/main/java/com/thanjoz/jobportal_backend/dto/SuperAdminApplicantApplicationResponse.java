package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import lombok.Data;



@Data
public class SuperAdminApplicantApplicationResponse {

    private Long id;

    private Long userId;

    private Long jobId;

    private String jobName;

    private ApplicationStatus status;

    private String name;

    private String email;

    private String phone;

    private String qualification;

    private String experience;

    private String resume;


}