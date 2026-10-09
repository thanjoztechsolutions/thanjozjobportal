package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.dto.ApplyJobResponse;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.service.ApplicationService;
import com.thanjoz.jobportal_backend.dto.InterviewRequest;
import com.thanjoz.jobportal_backend.dto.StatusUpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    // ============================================================
    // APPLY FOR JOB
    // ============================================================

    @PostMapping(
            value = "/apply/{jobId}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> applyJob(
            @PathVariable Long jobId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("qualification") String qualification,
            @RequestParam("experience") String experience,
            @RequestParam(
                    value = "resume",
                    required = false
            )
            MultipartFile resume,
            Principal principal) {

        try {

            ApplyJobResponse response =
                    applicationService.applyJob(
                            jobId,
                            principal.getName(),
                            name,
                            email,
                            phone,
                            qualification,
                            experience,
                            resume
                    );

            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // GET APPLICATIONS OF ONE USER - PAGINATED
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getApplicationsByUser(

            @PathVariable Long userId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            Principal principal) {


        try {

            Pageable pageable =
                    PageRequest.of(page, size);


            Page<ApplicationResponse> applications =
                    applicationService.getApplicationsByUser(
                            userId,
                            pageable,
                            principal.getName()
                    );


            return ResponseEntity.ok(applications);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // GET ALL APPLICATIONS FOR ONE JOB - PAGINATED
    // ============================================================

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicationsByJob(

            @PathVariable Long jobId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            Principal principal) {

        try {

            Pageable pageable =
                    PageRequest.of(page, size);


            Page<Application> applications =
                    applicationService.getApplicationsByJob(
                            jobId,
                            pageable,
                            principal.getName()
                    );


            return ResponseEntity.ok(applications);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // GET APPLICATIONS FOR CONTRACTOR - PAGINATED
    // ============================================================

    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<?> getApplicationsByContractor(

            @PathVariable String contractorId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            Principal principal) {

        try {

            Pageable pageable =
                    PageRequest.of(page, size);


            Page<ApplicationResponse> applications =
                    applicationService.getApplicationsByContractor(
                            contractorId,
                            pageable,
                            principal.getName()
                    );


            return ResponseEntity.ok(applications);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // UPDATE APPLICATION STATUS
    // ============================================================

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody StatusUpdateRequest request,
            Principal principal) {

        try {

            Application application =
                    applicationService.updateApplicationStatus(
                            applicationId,
                            request.getStatus(),
                            principal.getName()
                    );

            return ResponseEntity.ok(application);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // DOWNLOAD / VIEW RESUME
    // ============================================================

    @GetMapping("/resume/{fileName}")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable String fileName,
            Principal principal) {

        try {

            applicationService.verifyResumeAccess(
                    fileName,
                    principal.getName()
            );


            Path filePath =
                    Paths.get("uploads/resumes")
                            .resolve(fileName)
                            .normalize();


            Resource resource =
                    new UrlResource(
                            filePath.toUri()
                    );


            if (!resource.exists()) {

                return ResponseEntity
                        .notFound()
                        .build();
            }


            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    resource.getFilename() +
                                    "\""
                    )
                    .header(
                            HttpHeaders.CONTENT_TYPE,
                            "application/pdf"
                    )
                    .body(resource);


        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();

        } catch (Exception e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    // ============================================================
    // SCHEDULE INTERVIEW
    // ============================================================

    @PostMapping("/{applicationId}/interview")
    public ResponseEntity<?> scheduleInterview(
            @PathVariable Long applicationId,
            @RequestBody InterviewRequest request,
            Principal principal) {

        try {

            Application application =
                    applicationService.scheduleInterview(
                            applicationId,
                            request,
                            principal.getName()
                    );

            return ResponseEntity.ok(application);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}