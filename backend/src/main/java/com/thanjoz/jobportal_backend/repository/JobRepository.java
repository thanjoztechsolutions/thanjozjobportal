package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    // ============================================================
    // SEARCH JOBS - PAGINATED
    // ============================================================

    Page<Job> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );


    // ============================================================
    // GET JOBS BY CONTRACTOR - PAGINATED
    // ============================================================

    Page<Job> findByContractorId(
            String contractorId,
            Pageable pageable
    );


    // ============================================================
    // GET ALL JOBS BY CONTRACTOR - NON PAGINATED
    // Used internally when finding contractor applications
    // ============================================================

    List<Job> findByContractorId(
            String contractorId
    );


    // ============================================================
    // FIND JOB BY ID AND CONTRACTOR
    // ============================================================

    Optional<Job> findByIdAndContractorId(
            Long id,
            String contractorId
    );


    // ============================================================
    // SUPER ADMIN - RECENT JOBS
    // ============================================================

    List<Job> findAllByOrderByIdDesc(
            Pageable pageable
    );
}