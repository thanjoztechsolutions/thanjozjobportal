package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import com.thanjoz.jobportal_backend.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class JobCategoryController {


    @Autowired
    private JobCategoryService jobCategoryService;



    // ================= CREATE CATEGORY =================

    @PostMapping
    public ResponseEntity<JobCategory> createCategory(
            @RequestBody JobCategory category
    ) {

        return ResponseEntity.ok(
                jobCategoryService.createCategory(category)
        );

    }



    // ================= GET ALL CATEGORIES =================

    @GetMapping
    public ResponseEntity<List<JobCategory>> getAllCategories() {

        return ResponseEntity.ok(
                jobCategoryService.getAllCategories()
        );

    }



    // ================= GET CATEGORY BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<JobCategory> getCategoryById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                jobCategoryService.getCategoryById(id)
        );

    }



    // ================= DELETE CATEGORY =================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id
    ) {

        jobCategoryService.deleteCategory(id);

        return ResponseEntity.ok(
                "Category deleted successfully"
        );

    }

}