package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.ApplicationRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import com.thanjoz.jobportal_backend.exception.AlreadyAppliedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;


    // APPLY FOR JOB
    public Application applyJob(Long jobId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        // CHECK DUPLICATE APPLICATION
        if (applicationRepository.existsByUserIdAndJobId(
                user.getId(),
                job.getId())) {

            throw new AlreadyAppliedException(
                    "You have already applied for this job");
        }

        Application application = new Application();

        application.setUserId(user.getId());
        application.setJobId(job.getId());
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }


    // GET APPLICATIONS OF ONE USER
    public List<ApplicationResponse> getApplicationsByUser(Long userId) {

        List<Application> applications =
                applicationRepository.findByUserId(userId);

        return applications.stream()
                .map(application -> {

                    Job job = jobRepository.findById(
                            application.getJobId()
                    ).orElse(null);

                    ApplicationResponse response =
                            new ApplicationResponse();

                    response.setId(application.getId());
                    response.setUserId(application.getUserId());
                    response.setJobId(application.getJobId());
                    response.setStatus(application.getStatus());

                    if (job != null) {
                        response.setJobTitle(job.getTitle());
                        response.setCompany(job.getCompany());
                        response.setLocation(job.getLocation());
                    }

                    return response;

                })
                .collect(Collectors.toList());
    }


    // GET ALL APPLICATIONS FOR ONE JOB
    public List<Application> getApplicationsByJob(Long jobId) {

        return applicationRepository.findByJobId(jobId);
    }
    public Application updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        application.setStatus(status);

        return applicationRepository.save(application);
    }
}