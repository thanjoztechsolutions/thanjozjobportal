package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    // ============================================================
    // GET APPLICATIONS BY USER - PAGINATED
    // ============================================================

    Page<Application> findByUserId(
            Long userId,
            Pageable pageable
    );


    // ============================================================
    // GET APPLICATIONS BY JOB - PAGINATED
    // ============================================================

    Page<Application> findByJobId(
            Long jobId,
            Pageable pageable
    );


    // ============================================================
    // GET APPLICATIONS BY MULTIPLE JOBS - PAGINATED
    // ============================================================

    Page<Application> findByJobIdIn(
            List<Long> jobIds,
            Pageable pageable
    );


    // ============================================================
    // CHECK DUPLICATE APPLICATION
    // ============================================================

    boolean existsByUserIdAndJobId(
            Long userId,
            Long jobId
    );


    // ============================================================
    // FIND APPLICATION BY RESUME
    // ============================================================

    Optional<Application> findByResume(
            String resume
    );


    // ============================================================
    // SUPER ADMIN DASHBOARD COUNTS
    // ============================================================

    long countByStatus(
            ApplicationStatus status
    );
}