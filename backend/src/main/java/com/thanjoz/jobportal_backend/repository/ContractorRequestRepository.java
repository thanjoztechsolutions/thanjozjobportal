package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.ContractorRegistrationRequest;
import com.thanjoz.jobportal_backend.entity.ContractorRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractorRequestRepository
        extends JpaRepository<ContractorRegistrationRequest, Long> {

    Optional<ContractorRegistrationRequest> findByEmail(String email);

    boolean existsByEmailAndStatus(
            String email,
            ContractorRequestStatus status
    );

    List<ContractorRegistrationRequest> findAllByOrderByIdDesc();
}