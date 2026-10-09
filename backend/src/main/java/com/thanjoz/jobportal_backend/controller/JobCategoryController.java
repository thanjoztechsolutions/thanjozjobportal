package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import com.thanjoz.jobportal_backend.service.JobCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-categories")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    @PostMapping
    public ResponseEntity<JobCategory> createCategory(
            @RequestBody JobCategory category) {

        return ResponseEntity.ok(
                jobCategoryService.createCategory(category)
        );
    }

    @GetMapping
    public ResponseEntity<List<JobCategory>> getAllCategories() {

        return ResponseEntity.ok(
                jobCategoryService.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategory> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobCategoryService.getCategoryById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategory> updateCategory(
            @PathVariable Long id,
            @RequestBody JobCategory category) {

        return ResponseEntity.ok(
                jobCategoryService.updateCategory(id, category)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id) {

        jobCategoryService.deleteCategory(id);

        return ResponseEntity.ok("Job category deleted successfully");
    }
}