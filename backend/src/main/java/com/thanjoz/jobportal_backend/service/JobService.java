package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.JobRequest;
import com.thanjoz.jobportal_backend.dto.JobResponse;
import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.JobCategory;
import com.thanjoz.jobportal_backend.repository.CompanyRepository;
import com.thanjoz.jobportal_backend.repository.JobCategoryRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private UserRepository userRepository;


    // ================= CREATE JOB =================

    public JobResponse createJob(
            JobRequest request,
            Long companyId
    ) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new RuntimeException("Company not found")
                );


        JobCategory category = jobCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found")
                );


        Job job = new Job();

        job.setTitle(request.getTitle());

        job.setDescription(
                request.getDescription()
        );

        job.setLocation(
                request.getLocation()
        );

        job.setSalary(
                request.getSalary()
        );

        job.setCompany(company);

        job.setCategory(category);


        Job savedJob =
                jobRepository.save(job);


        JobResponse response =
                new JobResponse();


        response.setId(savedJob.getId());

        response.setTitle(
                savedJob.getTitle()
        );

        response.setDescription(
                savedJob.getDescription()
        );

        response.setLocation(
                savedJob.getLocation()
        );

        response.setSalary(
                savedJob.getSalary()
        );

        response.setCompanyId(
                savedJob.getCompany().getId()
        );

        response.setCategoryId(
                savedJob.getCategory().getId()
        );


        return response;
    }



    // ================= GET ALL JOBS =================

    public List<JobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= GET JOBS BY COMPANY =================

    public List<JobResponse> getJobsByCompany(Long companyId) {

        return jobRepository.findByCompanyId(companyId)
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= SEARCH JOB =================

    public List<JobResponse> searchJobs(String keyword) {

        return jobRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= SEARCH BY LOCATION =================

    public List<JobResponse> searchByLocation(String location) {

        return jobRepository
                .findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= SEARCH BY CATEGORY =================

    public List<JobResponse> searchByCategory(String category) {

        return jobRepository
                .findByCategory_NameIgnoreCase(category)
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= SEARCH BY COMPANY =================

    public List<JobResponse> searchByCompany(String company) {

        return jobRepository
                .findByCompany_NameContainingIgnoreCase(company)
                .stream()
                .map(this::convertToResponse)
                .toList();

    }



    // ================= GET JOB BY ID =================

    public JobResponse getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );


        JobResponse response = new JobResponse();


        response.setId(job.getId());

        response.setTitle(
                job.getTitle()
        );

        response.setDescription(
                job.getDescription()
        );

        response.setLocation(
                job.getLocation()
        );

        response.setSalary(
                job.getSalary()
        );


        response.setCompanyId(
                job.getCompany().getId()
        );


        response.setCategoryId(
                job.getCategory().getId()
        );


        return response;
    }



    // ================= CONVERT =================

    private JobResponse convertToResponse(Job job) {

        JobResponse response = new JobResponse();


        response.setId(job.getId());

        response.setTitle(
                job.getTitle()
        );

        response.setDescription(
                job.getDescription()
        );

        response.setLocation(
                job.getLocation()
        );

        response.setSalary(
                job.getSalary()
        );


        response.setCompanyId(
                job.getCompany().getId()
        );


        response.setCategoryId(
                job.getCategory().getId()
        );


        return response;

    }
// ================= UPDATE JOB =================

    public JobResponse updateJob(
            Long id,
            JobRequest request
    ) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );


        checkJobOwner(job);


        JobCategory category =
                jobCategoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException("Category not found")
                        );


        job.setTitle(
                request.getTitle()
        );

        job.setDescription(
                request.getDescription()
        );

        job.setLocation(
                request.getLocation()
        );

        job.setSalary(
                request.getSalary()
        );

        job.setCategory(category);


        Job updatedJob =
                jobRepository.save(job);


        return convertToResponse(updatedJob);
    }
    // ================= DELETE JOB =================

    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );


        checkJobOwner(job);


        jobRepository.delete(job);

    }
    // ================= CHECK JOB OWNER =================

    private void checkJobOwner(Job job) {


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        String email =
                authentication.getName();


        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found")
                        );


        if(!job.getCompany()
                .getOwner()
                .getId()
                .equals(user.getId())) {


            throw new RuntimeException(
                    "You are not the owner of this company"
            );

        }

    }
}