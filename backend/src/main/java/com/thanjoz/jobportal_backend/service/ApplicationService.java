package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.ApplicationRequest;
import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.dto.ApplicationStatusRequest;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.enums.ApplicationStatus;
import com.thanjoz.jobportal_backend.repository.ApplicationRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    // ================= APPLY FOR JOB =================

    public ApplicationResponse applyJob(
            Long jobId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // ================= CHECK DUPLICATE APPLICATION =================

        if (applicationRepository.existsByUserIdAndJobId(
                user.getId(),
                job.getId()
        )) {

            throw new RuntimeException("You have already applied for this job");

        }

        Application application = new Application();

        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);

        Application savedApplication =
                applicationRepository.save(application);

        ApplicationResponse response = new ApplicationResponse();

        response.setId(savedApplication.getId());
        response.setUserId(user.getId());
        response.setUserName(user.getFirstName() + " " + user.getLastName());
        response.setJobId(job.getId());
        response.setJobTitle(job.getTitle());
        response.setStatus(savedApplication.getStatus().name());

        return response;
    }

    // ================= GET APPLICATIONS BY JOB =================

    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(application -> {

                    ApplicationResponse response = new ApplicationResponse();

                    response.setId(application.getId());

                    response.setUserId(application.getUser().getId());

                    response.setUserName(
                            application.getUser().getFirstName()
                                    + " "
                                    + application.getUser().getLastName()
                    );

                    response.setJobId(application.getJob().getId());

                    response.setJobTitle(
                            application.getJob().getTitle()
                    );

                    response.setStatus(application.getStatus().name());

                    return response;

                })
                .toList();
    }

    // ================= GET APPLICATIONS BY USER =================

    public List<ApplicationResponse> getApplicationsByUser(Long userId) {

        return applicationRepository.findByUserId(userId)
                .stream()
                .map(application -> {

                    ApplicationResponse response = new ApplicationResponse();

                    response.setId(application.getId());

                    response.setUserId(application.getUser().getId());

                    response.setUserName(
                            application.getUser().getFirstName()
                                    + " "
                                    + application.getUser().getLastName()
                    );

                    response.setJobId(application.getJob().getId());

                    response.setJobTitle(
                            application.getJob().getTitle()
                    );

                    response.setStatus(application.getStatus().name());

                    return response;

                })
                .toList();
    }

    // ================= UPDATE APPLICATION STATUS =================

    public String updateApplicationStatus(
            Long applicationId,
            ApplicationStatusRequest request
    ) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found")
                );

        application.setStatus(
                ApplicationStatus.valueOf(
                        request.getStatus().toUpperCase()
                )
        );

        applicationRepository.save(application);

        return "Application status updated successfully";
    }
}