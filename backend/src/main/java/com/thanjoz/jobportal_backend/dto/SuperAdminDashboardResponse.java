package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.entity.Job;
import java.util.List;
import com.thanjoz.jobportal_backend.entity.User;

import lombok.Data;

@Data
public class SuperAdminDashboardResponse {

    private long totalJobs;

    private long totalContractors;

    private long totalApplicants;

    private long totalApplications;

    private long pendingApplications;

    private long acceptedApplications;

    private long rejectedApplications;

    private long interviewScheduled;

    // Recent jobs
    private List<Job> recentJobs;

    // Recent contractors
    private List<User> recentContractors;

    // Recent applicants
    private List<User> recentApplicants;
}