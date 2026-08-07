package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.dto.ReviewRequest;
import com.thanjoz.jobportal_backend.dto.ReviewResponse;
import com.thanjoz.jobportal_backend.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    // ================= ADD REVIEW =================

    @PostMapping("/{companyId}")
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long companyId,
            @RequestBody ReviewRequest request
    ) {

        ReviewResponse response =
                reviewService.addReview(companyId, request);

        return ResponseEntity.ok(response);
    }


    // ================= GET COMPANY REVIEWS =================

    @GetMapping("/{companyId}")
    public ResponseEntity<List<ReviewResponse>> getCompanyReviews(
            @PathVariable Long companyId
    ) {

        return ResponseEntity.ok(
                reviewService.getCompanyReviews(companyId)
        );
    }


    // ================= GET COMPANY AVERAGE RATING =================

    @GetMapping("/{companyId}/rating")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long companyId
    ) {

        return ResponseEntity.ok(
                reviewService.getAverageRating(companyId)
        );
    }

}