package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
}
