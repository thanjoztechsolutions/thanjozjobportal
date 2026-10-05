package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.SuperAdminDashboardResponse;
import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.repository.ApplicationRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.thanjoz.jobportal_backend.entity.User;
import java.util.List;
import com.thanjoz.jobportal_backend.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.dto.SuperAdminReportResponse;
import com.thanjoz.jobportal_backend.dto.SuperAdminApplicantApplicationResponse;

@Service
public class SuperAdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public SuperAdminService(
            UserRepository userRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository) {

        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }


    // ============================================================
    // SUPER ADMIN DASHBOARD
    // ============================================================

    public SuperAdminDashboardResponse getDashboardStatistics() {

        SuperAdminDashboardResponse response =
                new SuperAdminDashboardResponse();


        // --------------------------------------------------------
        // TOTAL JOBS
        // --------------------------------------------------------

        response.setTotalJobs(
                jobRepository.count()
        );


        // --------------------------------------------------------
        // TOTAL CONTRACTORS
        // --------------------------------------------------------

        response.setTotalContractors(
                userRepository.countByRole(Role.CONTRACTOR)
        );


        // --------------------------------------------------------
        // TOTAL APPLICANTS
        // --------------------------------------------------------

        response.setTotalApplicants(
                userRepository.countByRole(Role.APPLICANT)
        );


        // --------------------------------------------------------
        // TOTAL APPLICATIONS
        // --------------------------------------------------------

        response.setTotalApplications(
                applicationRepository.count()
        );


        // --------------------------------------------------------
        // REJECTED APPLICATIONS
        // --------------------------------------------------------

        response.setRejectedApplications(
                applicationRepository.countByStatus(
                        ApplicationStatus.REJECTED
                )
        );


        // --------------------------------------------------------
        // SELECTED APPLICATIONS
        // --------------------------------------------------------

        response.setAcceptedApplications(
                applicationRepository.countByStatus(
                        ApplicationStatus.SELECTED
                )
        );


        // --------------------------------------------------------
        // INTERVIEW SCHEDULED
        // --------------------------------------------------------

        response.setInterviewScheduled(
                applicationRepository.countByStatus(
                        ApplicationStatus.INTERVIEW_SCHEDULED
                )
        );


        // --------------------------------------------------------
        // PENDING APPLICATIONS
        // --------------------------------------------------------

        long pendingApplications =
                applicationRepository.countByStatus(
                        ApplicationStatus.APPLIED
                )
                        +
                        applicationRepository.countByStatus(
                                ApplicationStatus.RESUME_REVIEWED
                        )
                        +
                        applicationRepository.countByStatus(
                                ApplicationStatus.SHORTLISTED
                        )
                        +
                        applicationRepository.countByStatus(
                                ApplicationStatus.INTERVIEW_SCHEDULED
                        );


        response.setPendingApplications(
                pendingApplications
        );


        // --------------------------------------------------------
        // RECENT JOBS
        // --------------------------------------------------------

        response.setRecentJobs(
                jobRepository.findAllByOrderByIdDesc(
                        PageRequest.of(0, 5)
                )
        );


        // --------------------------------------------------------
        // RECENT CONTRACTORS
        // --------------------------------------------------------

        response.setRecentContractors(
                userRepository.findByRoleOrderByIdDesc(
                        Role.CONTRACTOR,
                        PageRequest.of(0, 5)
                )
        );


        // --------------------------------------------------------
        // RECENT APPLICANTS
        // --------------------------------------------------------

        response.setRecentApplicants(
                userRepository.findByRoleOrderByIdDesc(
                        Role.APPLICANT,
                        PageRequest.of(0, 5)
                )
        );

        return response;
    }


    // ============================================================
    // GET ALL CONTRACTORS
    // ============================================================

    public List<User> getAllContractors() {

        return userRepository.findByRole(Role.CONTRACTOR);
    }


    // ============================================================
    // GET CONTRACTOR BY ID
    // ============================================================

    public User getContractorById(Long id) {

        return userRepository.findById(id)
                .filter(user ->
                        user.getRole() == Role.CONTRACTOR)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contractor not found"));
    }


    // ============================================================
    // GET JOBS POSTED BY CONTRACTOR - PAGINATED
    // ============================================================

    public Page<Job> getJobsByContractor(
            String contractorId,
            Pageable pageable) {

        // Verify contractor exists
        userRepository.findByContractorId(contractorId)
                .filter(user ->
                        user.getRole() == Role.CONTRACTOR)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contractor not found"));

        return jobRepository.findByContractorId(
                contractorId,
                pageable
        );
    }


    // ============================================================
    // GET APPLICATIONS FOR CONTRACTOR'S JOBS - PAGINATED
    // ============================================================

    public Page<Application> getApplicationsByContractor(
            String contractorId,
            Pageable pageable) {

        // --------------------------------------------------------
        // VERIFY CONTRACTOR EXISTS
        // --------------------------------------------------------

        userRepository.findByContractorId(contractorId)
                .filter(user ->
                        user.getRole() == Role.CONTRACTOR)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contractor not found"));


        // --------------------------------------------------------
        // FIND ALL JOBS OF CONTRACTOR
        // --------------------------------------------------------

        List<Job> jobs =
                jobRepository.findByContractorId(
                        contractorId
                );


        // --------------------------------------------------------
        // NO JOBS
        // --------------------------------------------------------

        if (jobs.isEmpty()) {

            return Page.empty(pageable);
        }


        // --------------------------------------------------------
        // GET JOB IDS
        // --------------------------------------------------------

        List<Long> jobIds =
                jobs.stream()
                        .map(Job::getId)
                        .toList();


        // --------------------------------------------------------
        // GET APPLICATIONS WITH PAGINATION
        // --------------------------------------------------------

        return applicationRepository.findByJobIdIn(
                jobIds,
                pageable
        );
    }


    // ============================================================
    // GET ALL APPLICANTS - SUPER ADMIN
    // ============================================================

    public List<User> getAllApplicants() {

        return userRepository.findByRole(Role.APPLICANT);
    }


    // ============================================================
    // GET APPLICANT BY ID - SUPER ADMIN
    // ============================================================

    public User getApplicantById(Long id) {

        return userRepository.findById(id)
                .filter(user ->
                        user.getRole() == Role.APPLICANT)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Applicant not found"));
    }


    // ============================================================
    // GET APPLICATIONS BY APPLICANT - SUPER ADMIN
    // ============================================================

    public List<SuperAdminApplicantApplicationResponse>
    getApplicationsByApplicant(Long userId) {

        // --------------------------------------------------------
        // VERIFY APPLICANT EXISTS
        // --------------------------------------------------------

        userRepository.findById(userId)
                .filter(user ->
                        user.getRole() == Role.APPLICANT)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Applicant not found"));


        // --------------------------------------------------------
        // GET ALL APPLICATIONS OF APPLICANT
        //
        // Repository currently supports pagination,
        // so request a large page here to preserve
        // your existing List return type.
        // --------------------------------------------------------

        Page<Application> applicationPage =
                applicationRepository.findByUserId(
                        userId,
                        PageRequest.of(0, Integer.MAX_VALUE)
                );

        List<Application> applications =
                applicationPage.getContent();


        // --------------------------------------------------------
        // CONVERT TO RESPONSE
        // --------------------------------------------------------

        return applications.stream()
                .map(application -> {

                    SuperAdminApplicantApplicationResponse response =
                            new SuperAdminApplicantApplicationResponse();


                    response.setId(
                            application.getId()
                    );

                    response.setUserId(
                            application.getUserId()
                    );

                    response.setJobId(
                            application.getJobId()
                    );


                    // Get job name

                    jobRepository.findById(
                            application.getJobId()
                    ).ifPresent(job ->
                            response.setJobName(
                                    job.getTitle()
                            )
                    );


                    response.setStatus(
                            application.getStatus()
                    );

                    response.setName(
                            application.getName()
                    );

                    response.setEmail(
                            application.getEmail()
                    );

                    response.setPhone(
                            application.getPhone()
                    );

                    response.setQualification(
                            application.getQualification()
                    );

                    response.setExperience(
                            application.getExperience()
                    );

                    response.setResume(
                            application.getResume()
                    );


                    return response;

                })
                .toList();
    }


    // ============================================================
    // SUPER ADMIN REPORTS
    // ============================================================

    public SuperAdminReportResponse getReports() {

        SuperAdminReportResponse response =
                new SuperAdminReportResponse();


        // --------------------------------------------------------
        // TOTAL JOBS
        // --------------------------------------------------------

        response.setTotalJobs(
                jobRepository.count()
        );


        // --------------------------------------------------------
        // TOTAL CONTRACTORS
        // --------------------------------------------------------

        response.setTotalContractors(
                userRepository.countByRole(Role.CONTRACTOR)
        );


        // --------------------------------------------------------
        // TOTAL APPLICANTS
        // --------------------------------------------------------

        response.setTotalApplicants(
                userRepository.countByRole(Role.APPLICANT)
        );


        // --------------------------------------------------------
        // TOTAL APPLICATIONS
        // --------------------------------------------------------

        long totalApplications =
                applicationRepository.count();

        response.setTotalApplications(
                totalApplications
        );


        // --------------------------------------------------------
        // APPLICATION STATUS COUNTS
        // --------------------------------------------------------

        long applied =
                applicationRepository.countByStatus(
                        ApplicationStatus.APPLIED
                );

        long resumeReviewed =
                applicationRepository.countByStatus(
                        ApplicationStatus.RESUME_REVIEWED
                );

        long shortlisted =
                applicationRepository.countByStatus(
                        ApplicationStatus.SHORTLISTED
                );

        long interviewScheduled =
                applicationRepository.countByStatus(
                        ApplicationStatus.INTERVIEW_SCHEDULED
                );

        long selected =
                applicationRepository.countByStatus(
                        ApplicationStatus.SELECTED
                );

        long rejected =
                applicationRepository.countByStatus(
                        ApplicationStatus.REJECTED
                );


        // --------------------------------------------------------
        // SET STATUS COUNTS
        // --------------------------------------------------------

        response.setAppliedApplications(
                applied
        );

        response.setResumeReviewedApplications(
                resumeReviewed
        );

        response.setShortlistedApplications(
                shortlisted
        );

        response.setInterviewScheduledApplications(
                interviewScheduled
        );

        response.setSelectedApplications(
                selected
        );

        response.setRejectedApplications(
                rejected
        );


        // --------------------------------------------------------
        // RECRUITMENT SUCCESS RATE
        // --------------------------------------------------------

        double successRate = 0.0;

        if (totalApplications > 0) {

            successRate =
                    ((double) selected / totalApplications) * 100;
        }

        response.setRecruitmentSuccessRate(
                Math.round(successRate * 100.0) / 100.0
        );


        return response;
    }
}