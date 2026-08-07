package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByCategory_NameIgnoreCase(String category);

    List<Job> findByCompany_NameContainingIgnoreCase(String company);

}