package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.SuperAdminDashboardResponse;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.service.JobService;
import com.thanjoz.jobportal_backend.service.SuperAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thanjoz.jobportal_backend.entity.User;
import java.util.List;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.dto.SuperAdminReportResponse;
import com.thanjoz.jobportal_backend.dto.SuperAdminApplicantApplicationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final JobService jobService;

    public SuperAdminController(
            SuperAdminService superAdminService,
            JobService jobService) {

        this.superAdminService = superAdminService;
        this.jobService = jobService;
    }


    // ============================================================
    // SUPER ADMIN DASHBOARD
    // ============================================================

    @GetMapping("/dashboard")
    public ResponseEntity<SuperAdminDashboardResponse>
    getDashboard() {

        SuperAdminDashboardResponse response =
                superAdminService.getDashboardStatistics();

        return ResponseEntity.ok(response);
    }


    // ============================================================
    // GET ALL JOBS - SUPER ADMIN
    // ============================================================

    @GetMapping("/jobs")
    public ResponseEntity<Page<Job>> getAllJobs(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                jobService.getAllJobs(pageable)
        );
    }


    // ============================================================
    // GET JOB BY ID - SUPER ADMIN
    // ============================================================

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );
    }


    // ============================================================
    // GET ALL CONTRACTORS
    // ============================================================

    @GetMapping("/contractors")
    public ResponseEntity<List<User>> getAllContractors() {

        return ResponseEntity.ok(
                superAdminService.getAllContractors()
        );
    }


    // ============================================================
    // GET CONTRACTOR BY ID
    // ============================================================

    @GetMapping("/contractors/{id}")
    public ResponseEntity<User> getContractorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getContractorById(id)
        );
    }


    // ============================================================
    // GET JOBS POSTED BY CONTRACTOR - PAGINATED
    // ============================================================

    @GetMapping("/contractors/{contractorId}/jobs")
    public ResponseEntity<Page<Job>> getJobsByContractor(

            @PathVariable String contractorId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                superAdminService.getJobsByContractor(
                        contractorId,
                        pageable
                )
        );
    }


    // ============================================================
    // GET APPLICATIONS FOR CONTRACTOR'S JOBS - PAGINATED
    // ============================================================

    @GetMapping("/contractors/{contractorId}/applications")
    public ResponseEntity<Page<Application>> getApplicationsByContractor(

            @PathVariable String contractorId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return ResponseEntity.ok(
                superAdminService.getApplicationsByContractor(
                        contractorId,
                        pageable
                )
        );
    }


    // ============================================================
    // GET ALL APPLICANTS
    // ============================================================

    @GetMapping("/applicants")
    public ResponseEntity<List<User>> getAllApplicants() {

        return ResponseEntity.ok(
                superAdminService.getAllApplicants()
        );
    }


    // ============================================================
    // GET APPLICANT BY ID
    // ============================================================

    @GetMapping("/applicants/{id}")
    public ResponseEntity<User> getApplicantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                superAdminService.getApplicantById(id)
        );
    }


    // ============================================================
    // GET APPLICATIONS BY APPLICANT
    // ============================================================

    @GetMapping("/applicants/{userId}/applications")
    public ResponseEntity<List<SuperAdminApplicantApplicationResponse>>
    getApplicationsByApplicant(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                superAdminService.getApplicationsByApplicant(userId)
        );
    }


    // ============================================================
    // SUPER ADMIN REPORTS
    // ============================================================

    @GetMapping("/reports")
    public ResponseEntity<SuperAdminReportResponse> getReports() {

        return ResponseEntity.ok(
                superAdminService.getReports()
        );
    }
}