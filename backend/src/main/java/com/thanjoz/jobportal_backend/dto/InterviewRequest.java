package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class InterviewRequest {

    private LocalDate date;

    private LocalTime time;

    private String mode;

    private String meetingLink;

    private String message;
}