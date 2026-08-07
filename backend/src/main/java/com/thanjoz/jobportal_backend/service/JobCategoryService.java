package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import com.thanjoz.jobportal_backend.repository.JobCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryService {


    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    // ================= CREATE CATEGORY =================

    public JobCategory createCategory(JobCategory category) {

        return jobCategoryRepository.save(category);

    }


    // ================= GET ALL CATEGORIES =================

    public List<JobCategory> getAllCategories() {

        return jobCategoryRepository.findAll();

    }


    // ================= GET CATEGORY BY ID =================

    public JobCategory getCategoryById(Long id) {

        return jobCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found")
                );

    }


    // ================= DELETE CATEGORY =================

    public void deleteCategory(Long id) {

        jobCategoryRepository.deleteById(id);

    }

}