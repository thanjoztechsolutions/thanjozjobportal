package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByContractorId(String contractorId);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByContractorId(String contractorId);

    // Super Admin dashboard counts
    long countByRole(Role role);

    // Super Admin - get all contractors
    List<User> findByRole(Role role);

    List<User> findByRoleOrderByIdDesc(
            Role role,
            org.springframework.data.domain.Pageable pageable
    );
}