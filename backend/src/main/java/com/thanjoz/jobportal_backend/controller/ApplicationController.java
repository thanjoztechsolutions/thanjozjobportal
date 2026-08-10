package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.service.ApplicationService;
import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    // APPLY FOR JOB
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyJob(
            @PathVariable Long jobId,
            Principal principal) {

        try {

            Application application =
                    applicationService.applyJob(
                            jobId,
                            principal.getName()
                    );

            return ResponseEntity.ok(application);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // GET APPLICATIONS OF ONE USER
    @GetMapping("/user/{userId}")
    public List<ApplicationResponse> getApplicationsByUser(
            @PathVariable Long userId) {

        return applicationService.getApplicationsByUser(userId);
    }


    // GET ALL APPLICATIONS FOR ONE JOB
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJob(
            @PathVariable Long jobId) {

        return applicationService.getApplicationsByJob(jobId);
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status) {

        Application application =
                applicationService.updateApplicationStatus(
                        applicationId,
                        status
                );

        return ResponseEntity.ok(application);
    }
}