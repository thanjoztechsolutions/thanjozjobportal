package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }
    @GetMapping("/company/{companyId}")
    public List<Job> getJobsByCompany(@PathVariable Long companyId) {
        return jobService.getJobsByCompany(companyId);
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }
    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody Job job,
            java.security.Principal principal) {

        if (!jobService.isJobOwner(id, principal.getName())) {
            throw new RuntimeException("You are not the owner of this company");
        }

        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id,
            java.security.Principal principal) {

        if (!jobService.isJobOwner(id, principal.getName())) {
            throw new RuntimeException("You are not the owner of this company");
        }

        jobService.deleteJob(id);

        return "Job deleted successfully";
    }
}