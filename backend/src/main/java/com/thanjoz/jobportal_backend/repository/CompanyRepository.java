package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


}