package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class SuperAdminReportResponse {

    private long totalJobs;

    private long totalContractors;

    private long totalApplicants;

    private long totalApplications;

    private long appliedApplications;

    private long resumeReviewedApplications;

    private long shortlistedApplications;

    private long interviewScheduledApplications;

    private long selectedApplications;

    private long rejectedApplications;

    private double recruitmentSuccessRate;
}