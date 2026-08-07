package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class ReviewResponse {

    private Long id;

    private Long userId;

    private String userName;

    private Long companyId;

    private String companyName;

    private int rating;

    private String comment;

}