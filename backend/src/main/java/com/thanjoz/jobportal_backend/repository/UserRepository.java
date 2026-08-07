package com.thanjoz.jobportal_backend.repository;

import com.thanjoz.jobportal_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);


    Optional<User> findByEmailOrPhoneNumber(
            String email,
            String phoneNumber
    );


}