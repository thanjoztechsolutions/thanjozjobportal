package com.thanjoz.jobportal_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "contractor_registration_requests")
@Data
public class ContractorRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // CONTRACTOR DETAILS
    // ============================================================

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    private String address;

    private String gst;

    private String companyName;

    private String companyLicense;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ============================================================
    // CONTRACTOR ID
    // ============================================================

    @Column(unique = true)
    private String contractorId;

    // ============================================================
    // REQUEST STATUS
    // ============================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractorRequestStatus status;

    // ============================================================
    // REJECTION INFORMATION
    // ============================================================

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    // ============================================================
    // AUDIT TIMESTAMPS
    // ============================================================

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ============================================================
    // SET DEFAULT VALUES BEFORE INSERT
    // ============================================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (status == null) {
            status = ContractorRequestStatus.PENDING;
        }
    }

    // ============================================================
    // UPDATE TIMESTAMP
    // ============================================================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}