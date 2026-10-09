package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import com.thanjoz.jobportal_backend.repository.JobCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;

    public JobCategoryService(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    public JobCategory createCategory(JobCategory category) {
        return jobCategoryRepository.save(category);
    }

    public List<JobCategory> getAllCategories() {
        return jobCategoryRepository.findAll();
    }

    public JobCategory getCategoryById(Long id) {
        return jobCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job category not found"));
    }

    public JobCategory updateCategory(Long id, JobCategory category) {

        JobCategory existingCategory = jobCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job category not found"));

        existingCategory.setName(category.getName());

        return jobCategoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {

        JobCategory existingCategory = jobCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job category not found"));

        jobCategoryRepository.delete(existingCategory);
    }
}