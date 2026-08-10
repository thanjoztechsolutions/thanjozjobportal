package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.CompanyRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Job updateJob(Long id, Job updatedJob) {

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setCompany(updatedJob.getCompany());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalary(updatedJob.getSalary());
        existingJob.setType(updatedJob.getType());
        existingJob.setExperience(updatedJob.getExperience());
        existingJob.setCompanyId(updatedJob.getCompanyId());
        existingJob.setCategoryId(updatedJob.getCategoryId());

        return jobRepository.save(existingJob);
    }

    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        jobRepository.delete(job);
    }

    public List<Job> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId);
    }

    public boolean isJobOwner(Long jobId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Company company = companyRepository.findById(job.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        return company.getOwner().getId().equals(user.getId());
    }
}

