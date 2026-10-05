package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.entity.ApplicationStatus;

public class StatusUpdateRequest {

    private ApplicationStatus status;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}