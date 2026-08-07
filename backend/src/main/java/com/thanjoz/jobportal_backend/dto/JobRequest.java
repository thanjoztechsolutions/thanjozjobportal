package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class JobRequest {

    private String title;

    private String description;

    private String location;

    private String salary;

    private Long companyId;

    private Long categoryId;
}