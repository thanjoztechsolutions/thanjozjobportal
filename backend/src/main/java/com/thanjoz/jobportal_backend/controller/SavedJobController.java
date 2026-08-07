package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.SavedJobRequest;
import com.thanjoz.jobportal_backend.dto.SavedJobResponse;
import com.thanjoz.jobportal_backend.service.SavedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
@CrossOrigin
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;


    // ================= SAVE JOB =================

    @PostMapping("/{jobId}")
    public ResponseEntity<SavedJobResponse> saveJob(
            @PathVariable Long jobId,
            @RequestBody SavedJobRequest request
    ) {

        SavedJobResponse response =
                savedJobService.saveJob(jobId, request);

        return ResponseEntity.ok(response);
    }


    // ================= GET SAVED JOBS =================

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedJobResponse>> getSavedJobs(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                savedJobService.getSavedJobs(userId)
        );
    }


    // ================= REMOVE SAVED JOB =================

    @DeleteMapping("/{userId}/{jobId}")
    public ResponseEntity<String> removeSavedJob(
            @PathVariable Long userId,
            @PathVariable Long jobId
    ) {

        return ResponseEntity.ok(
                savedJobService.removeSavedJob(userId, jobId)
        );
    }

}