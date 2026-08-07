package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.JobRequest;
import com.thanjoz.jobportal_backend.dto.JobResponse;
import com.thanjoz.jobportal_backend.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;


    // ================= CREATE JOB =================

    @PostMapping("/create/{companyId}")
    public ResponseEntity<JobResponse> createJob(

            @PathVariable Long companyId,

            @RequestBody JobRequest request

    ) {

        JobResponse response =
                jobService.createJob(
                        request,
                        companyId
                );

        return ResponseEntity.ok(response);

    }



    // ================= GET ALL JOBS =================

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );

    }



    // ================= GET JOBS BY COMPANY =================

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(

            @PathVariable Long companyId

    ) {

        return ResponseEntity.ok(
                jobService.getJobsByCompany(companyId)
        );

    }



    // ================= SEARCH JOBS =================

    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(

            @RequestParam String keyword

    ) {

        return ResponseEntity.ok(
                jobService.searchJobs(keyword)
        );

    }



    // ================= SEARCH BY LOCATION =================

    @GetMapping("/location")
    public ResponseEntity<List<JobResponse>> searchByLocation(

            @RequestParam String location

    ) {

        return ResponseEntity.ok(
                jobService.searchByLocation(location)
        );

    }



    // ================= SEARCH BY CATEGORY =================

    @GetMapping("/category")
    public ResponseEntity<List<JobResponse>> searchByCategory(

            @RequestParam String category

    ) {

        return ResponseEntity.ok(
                jobService.searchByCategory(category)
        );

    }



    // ================= SEARCH BY COMPANY NAME =================

    @GetMapping("/company")
    public ResponseEntity<List<JobResponse>> searchByCompany(

            @RequestParam String company

    ) {

        return ResponseEntity.ok(
                jobService.searchByCompany(company)
        );

    }



    // ================= GET JOB BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );

    }



    // ================= UPDATE JOB =================

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(

            @PathVariable Long id,

            @RequestBody JobRequest request

    ) {

        return ResponseEntity.ok(
                jobService.updateJob(id, request)
        );

    }
// ================= DELETE JOB =================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(

            @PathVariable Long id

    ) {

        jobService.deleteJob(id);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );

    }
}