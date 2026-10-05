package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "applications")
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long jobId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    // Applicant details stored at the time of application
    private String name;

    private String email;

    private String phone;

    private String qualification;

    private String experience;

    private String resume;

    // Interview details
    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private String interviewMode;

    private String meetingLink;

    private String interviewMessage;
}