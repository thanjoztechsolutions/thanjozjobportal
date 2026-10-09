package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.SavedJob;
import com.thanjoz.jobportal_backend.service.SavedJobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping("/save/{userId}/{jobId}")
    public ResponseEntity<SavedJob> saveJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                savedJobService.saveJob(userId, jobId)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedJob>> getSavedJobs(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                savedJobService.getSavedJobs(userId)
        );
    }

    @DeleteMapping("/remove/{userId}/{jobId}")
    public ResponseEntity<String> removeSavedJob(
            @PathVariable Long userId,
            @PathVariable Long jobId) {

        savedJobService.removeSavedJob(userId, jobId);

        return ResponseEntity.ok("Job removed from saved jobs");
    }
}