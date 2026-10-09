package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import com.thanjoz.jobportal_backend.dto.UpdateJobRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;


    // ============================================================
    // GET ALL JOBS - PAGINATED
    // ============================================================

    @GetMapping
    public Page<Job> getAllJobs(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return jobService.getAllJobs(pageable);
    }


    // ============================================================
    // SEARCH JOBS - PAGINATED
    // ============================================================

    @GetMapping("/search")
    public Page<Job> searchJobs(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return jobService.searchJobs(
                keyword,
                pageable
        );
    }


    // ============================================================
    // GET JOBS BY CONTRACTOR - PAGINATED
    // ============================================================

    @GetMapping("/contractor/{contractorId}")
    public Page<Job> getJobsByContractor(

            @PathVariable String contractorId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return jobService.getJobsByContractor(
                contractorId,
                pageable
        );
    }


    // ============================================================
    // GET JOB BY ID
    // ============================================================

    @GetMapping("/{id}")
    public Job getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id);
    }


    // ============================================================
    // CREATE JOB USING CONTRACTOR ID
    // ============================================================

    @PostMapping("/create/{contractorId}")
    public Job createJob(
            @PathVariable String contractorId,
            @RequestBody Job job) {

        return jobService.createJob(
                job,
                contractorId
        );
    }


    // ============================================================
    // UPDATE JOB
    // ============================================================

    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody UpdateJobRequest request,
            Principal principal) {

        if (!jobService.isJobOwner(
                id,
                principal.getName())) {

            throw new RuntimeException(
                    "You are not the owner of this job");
        }

        return jobService.updateJob(
                id,
                request
        );
    }


    // ============================================================
    // DELETE JOB
    // ============================================================

    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id,
            Principal principal) {

        if (!jobService.isJobOwner(
                id,
                principal.getName())) {

            throw new RuntimeException(
                    "You are not the owner of this job");
        }


        jobService.deleteJob(id);


        return "Job deleted successfully";
    }
}