package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.ReviewRequest;
import com.thanjoz.jobportal_backend.dto.ReviewResponse;
import com.thanjoz.jobportal_backend.entity.Company;
import com.thanjoz.jobportal_backend.entity.Review;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.CompanyRepository;
import com.thanjoz.jobportal_backend.repository.ReviewRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;


    // ================= ADD REVIEW =================

    public ReviewResponse addReview(
            Long companyId,
            ReviewRequest request
    ) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new RuntimeException("Company not found")
                );

        if (reviewRepository.existsByUserIdAndCompanyId(
                user.getId(),
                company.getId()
        )) {

            throw new RuntimeException(
                    "You have already reviewed this company"
            );

        }

        Review review = new Review();

        review.setUser(user);
        review.setCompany(company);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        ReviewResponse response = new ReviewResponse();

        response.setId(savedReview.getId());
        response.setUserId(user.getId());
        response.setUserName(
                user.getFirstName() + " " + user.getLastName()
        );
        response.setCompanyId(company.getId());
        response.setCompanyName(company.getName());
        response.setRating(savedReview.getRating());
        response.setComment(savedReview.getComment());

        return response;
    }


    // ================= GET COMPANY REVIEWS =================

    public List<ReviewResponse> getCompanyReviews(Long companyId) {

        return reviewRepository.findByCompanyId(companyId)
                .stream()
                .map(review -> {

                    ReviewResponse response =
                            new ReviewResponse();

                    response.setId(review.getId());

                    response.setUserId(
                            review.getUser().getId()
                    );

                    response.setUserName(
                            review.getUser().getFirstName()
                                    + " "
                                    + review.getUser().getLastName()
                    );

                    response.setCompanyId(
                            review.getCompany().getId()
                    );

                    response.setCompanyName(
                            review.getCompany().getName()
                    );

                    response.setRating(
                            review.getRating()
                    );

                    response.setComment(
                            review.getComment()
                    );

                    return response;

                })
                .toList();
    }


    // ================= GET AVERAGE RATING =================

    public double getAverageRating(Long companyId) {

        List<Review> reviews =
                reviewRepository.findByCompanyId(companyId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double total = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();

        return total / reviews.size();
    }

}