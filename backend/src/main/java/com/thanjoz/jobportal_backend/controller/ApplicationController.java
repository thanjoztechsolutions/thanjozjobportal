package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.ApplicationRequest;
import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.dto.ApplicationStatusRequest;
import com.thanjoz.jobportal_backend.service.ApplicationService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    // ================= APPLY FOR JOB =================

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<ApplicationResponse> applyJob(

            @PathVariable Long jobId,

            @RequestBody ApplicationRequest request,

            Principal principal

    ) {

        ApplicationResponse response =
                applicationService.applyJob(
                        jobId,
                        principal.getName()
                );

        return ResponseEntity.ok(response);
    }


    // ================= GET APPLICATIONS BY JOB =================

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(
            @PathVariable Long jobId
    ) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(jobId)
        );
    }


    // ================= GET APPLICATIONS BY USER =================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByUser(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByUser(userId)
        );
    }


    // ================= UPDATE APPLICATION STATUS =================

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(

            @PathVariable Long applicationId,

            @RequestBody ApplicationStatusRequest request

    ) {

        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(
                        applicationId,
                        request
                )
        );

    }

}