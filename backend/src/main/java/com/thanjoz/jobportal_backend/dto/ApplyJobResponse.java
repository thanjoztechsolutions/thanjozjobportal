package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class ApplyJobResponse {

    private String message;
    private String contractorPhone;
    private Long applicationId;

    public ApplyJobResponse(
            String message,
            String contractorPhone,
            Long applicationId) {

        this.message = message;
        this.contractorPhone = contractorPhone;
        this.applicationId = applicationId;
    }
}