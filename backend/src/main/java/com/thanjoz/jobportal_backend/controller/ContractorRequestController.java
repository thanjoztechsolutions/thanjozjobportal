package com.thanjoz.jobportal_backend.controller;

import com.thanjoz.jobportal_backend.entity.ContractorRegistrationRequest;
import com.thanjoz.jobportal_backend.service.ContractorRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ContractorRequestController {

    private final ContractorRequestService contractorRequestService;

    public ContractorRequestController(
            ContractorRequestService contractorRequestService) {

        this.contractorRequestService =
                contractorRequestService;
    }


    // ============================================================
    // CREATE CONTRACTOR REGISTRATION REQUEST
    // PUBLIC
    //
    // POST /api/contractor-requests
    // ============================================================

    @PostMapping("/api/contractor-requests")
    public ResponseEntity<?> createRequest(
            @RequestBody ContractorRegistrationRequest request) {

        try {

            ContractorRegistrationRequest savedRequest =
                    contractorRequestService.createRequest(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedRequest);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Invalid contractor registration request"
                    );
        }
    }


    // ============================================================
    // GET ALL CONTRACTOR REQUESTS
    // SUPER ADMIN ONLY
    //
    // GET /api/superadmin/contractor-requests
    // ============================================================

    @GetMapping("/api/superadmin/contractor-requests")
    public ResponseEntity<List<ContractorRegistrationRequest>>
    getAllRequests() {

        return ResponseEntity.ok(
                contractorRequestService.getAllRequests()
        );
    }


    // ============================================================
    // GET ONE CONTRACTOR REQUEST
    // SUPER ADMIN ONLY
    //
    // GET /api/superadmin/contractor-requests/{id}
    // ============================================================

    @GetMapping("/api/superadmin/contractor-requests/{id}")
    public ResponseEntity<?> getRequestById(
            @PathVariable Long id) {

        try {

            ContractorRegistrationRequest request =
                    contractorRequestService.getRequestById(id);

            return ResponseEntity.ok(request);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // APPROVE CONTRACTOR REQUEST
    // SUPER ADMIN ONLY
    //
    // PUT /api/superadmin/contractor-requests/{id}/approve
    // ============================================================

    @PutMapping(
            "/api/superadmin/contractor-requests/{id}/approve"
    )
    public ResponseEntity<?> approveRequest(
            @PathVariable Long id) {

        try {

            ContractorRegistrationRequest request =
                    contractorRequestService.approveRequest(id);

            return ResponseEntity.ok(request);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // REJECT CONTRACTOR REQUEST
    // SUPER ADMIN ONLY
    //
    // PUT /api/superadmin/contractor-requests/{id}/reject
    // ============================================================

    @PutMapping(
            "/api/superadmin/contractor-requests/{id}/reject"
    )
    public ResponseEntity<?> rejectRequest(
            @PathVariable Long id,
            @RequestBody(required = false)
            RejectRequest rejectRequest) {

        try {

            String reason = null;

            if (rejectRequest != null) {

                reason = rejectRequest.getReason();
            }


            ContractorRegistrationRequest request =
                    contractorRequestService.rejectRequest(
                            id,
                            reason
                    );

            return ResponseEntity.ok(request);

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // ============================================================
    // REJECT REQUEST DTO
    // ============================================================

    public static class RejectRequest {

        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}