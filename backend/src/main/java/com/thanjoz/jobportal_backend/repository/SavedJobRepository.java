package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    boolean existsByUserIdAndJobId(
            Long userId,
            Long jobId
    );

    List<SavedJob> findByUserId(Long userId);

    void deleteByUserIdAndJobId(
            Long userId,
            Long jobId
    );

}