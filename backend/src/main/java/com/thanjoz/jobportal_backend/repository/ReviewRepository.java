package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCompanyId(Long companyId);

    boolean existsByUserIdAndCompanyId(
            Long userId,
            Long companyId
    );

}