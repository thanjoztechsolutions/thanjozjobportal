package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    List<Application> findByJobId(Long jobId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}