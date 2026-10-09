package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import com.thanjoz.jobportal_backend.dto.UpdateJobRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;


    // ============================================================
    // GET ALL JOBS - PAGINATED
    // ============================================================

    public Page<Job> getAllJobs(Pageable pageable) {

        return jobRepository.findAll(pageable);
    }


    // ============================================================
    // GET JOB BY ID
    // ============================================================

    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));
    }


    // ============================================================
    // CREATE JOB USING CONTRACTOR ID
    // ============================================================

    public Job createJob(Job job, String contractorId) {

        User contractor = userRepository
                .findByContractorId(contractorId)
                .orElseThrow(() ->
                        new RuntimeException("Contractor not found"));


        // Make sure the user is actually a contractor
        if (contractor.getRole() == null ||
                !contractor.getRole().name().equals("CONTRACTOR")) {

            throw new RuntimeException(
                    "Only contractors can post jobs");
        }


        // SET CONTRACTOR ID AUTOMATICALLY
        job.setContractorId(contractor.getContractorId());


        return jobRepository.save(job);
    }


    // ============================================================
    // SEARCH JOBS - PAGINATED
    // ============================================================

    public Page<Job> searchJobs(
            String keyword,
            Pageable pageable) {

        return jobRepository
                .findByTitleContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }



    // ============================================================
// UPDATE JOB - PARTIAL UPDATE
// ============================================================

    public Job updateJob(
            Long id,
            UpdateJobRequest request) {

        Job existingJob = jobRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (request.getTitle() != null) {
            existingJob.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            existingJob.setDescription(
                    request.getDescription());
        }

        if (request.getCompany() != null) {
            existingJob.setCompany(
                    request.getCompany());
        }

        if (request.getLocation() != null) {
            existingJob.setLocation(
                    request.getLocation());
        }

        if (request.getSalary() != null) {
            existingJob.setSalary(
                    request.getSalary());
        }

        if (request.getType() != null) {
            existingJob.setType(
                    request.getType());
        }

        if (request.getExperience() != null) {
            existingJob.setExperience(
                    request.getExperience());
        }

        if (request.getCategory() != null) {
            existingJob.setCategory(
                    request.getCategory());

        }
            if (request.getVacancies() != null) {
                existingJob.setVacancies(request.getVacancies());

        }
        if (request.getRequiredSkill() != null) {
            existingJob.setRequiredSkill(
                    request.getRequiredSkill()
            );
        }

        // contractorId is intentionally NOT updated

        return jobRepository.save(existingJob);
    }


    // ============================================================
    // DELETE JOB
    // ============================================================

    public void deleteJob(Long id) {

        Job job = jobRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        jobRepository.delete(job);
    }


    // ============================================================
    // GET JOBS BY CONTRACTOR - PAGINATED
    // ============================================================

    public Page<Job> getJobsByContractor(
            String contractorId,
            Pageable pageable) {

        return jobRepository
                .findByContractorId(
                        contractorId,
                        pageable
                );
    }


    // ============================================================
    // CHECK WHETHER CONTRACTOR OWNS JOB
    // ============================================================

    public boolean isJobOwner(
            Long jobId,
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));


        // Check whether the job belongs
        // to this contractor

        return job.getContractorId() != null
                && user.getContractorId() != null
                && job.getContractorId()
                .equals(user.getContractorId());
    }
}