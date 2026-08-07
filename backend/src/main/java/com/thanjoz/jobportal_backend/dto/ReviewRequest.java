package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long userId;

    private int rating;

    private String comment;

}