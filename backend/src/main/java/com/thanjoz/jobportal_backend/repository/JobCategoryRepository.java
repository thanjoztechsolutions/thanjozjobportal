package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

}