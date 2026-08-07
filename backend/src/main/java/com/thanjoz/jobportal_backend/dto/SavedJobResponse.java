package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class SavedJobResponse {

    private Long id;

    private Long userId;

    private String userName;

    private Long jobId;

    private String jobTitle;

}