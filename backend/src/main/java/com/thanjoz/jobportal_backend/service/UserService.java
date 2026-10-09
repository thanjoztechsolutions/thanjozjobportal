package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.LoginRequest;
import com.thanjoz.jobportal_backend.dto.LoginResponse;
import com.thanjoz.jobportal_backend.dto.RegisterRequest;
import com.thanjoz.jobportal_backend.dto.RegisterResponse;
import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import com.thanjoz.jobportal_backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EmailService emailService) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.jwtService =
                jwtService;

        this.emailService =
                emailService;
    }


    // ============================================================
    // REGISTER USER
    // ============================================================

    public RegisterResponse registerUser(RegisterRequest request) {

        // --------------------------------------------------------
        // NORMALIZE EMAIL
        // --------------------------------------------------------

        String email = request.getEmail();

        if (email != null) {
            email = email.trim().toLowerCase();
            request.setEmail(email);
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE EMAIL
        // --------------------------------------------------------

        if (userRepository.existsByEmail(email)) {

            return new RegisterResponse(
                    "Email already registered",
                    null
            );
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE PHONE
        // --------------------------------------------------------

        if (request.getPhone() != null &&
                !request.getPhone().isBlank() &&
                userRepository.existsByPhone(request.getPhone())) {

            return new RegisterResponse(
                    "Phone number already registered",
                    null
            );
        }


        // ========================================================
        // APPLICANT REGISTRATION
        // ========================================================

        if ("APPLICANT".equalsIgnoreCase(request.getRole())) {

            User user = new User();

            user.setEmail(email);

            user.setPhone(
                    request.getPhone()
            );

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );

            user.setRole(
                    Role.APPLICANT
            );

            user.setFirstName(
                    request.getFirstName()
            );

            user.setLastName(
                    request.getLastName()
            );

            user.setResume(
                    request.getResume()
            );

            user.setStatus(
                    "Active"
            );


            // ----------------------------------------------------
            // SAVE APPLICANT
            // ----------------------------------------------------

            userRepository.save(user);


            // ----------------------------------------------------
            // SEND APPLICANT REGISTRATION EMAIL
            // ----------------------------------------------------

            emailService.sendApplicantRegistrationEmail(
                    user.getEmail()
            );


            // ----------------------------------------------------
            // RETURN RESPONSE
            // ----------------------------------------------------

            return new RegisterResponse(
                    "Registration successful",
                    user.getContractorId()
            );
        }


        // ========================================================
        // INVALID ROLE
        // ========================================================

        else {

            return new RegisterResponse(
                    "Invalid role",
                    null
            );
        }
    }


    // ============================================================
    // LOGIN
    // ============================================================

    public LoginResponse loginUser(LoginRequest request) {

        User user = null;


        // ========================================================
        // APPLICANT LOGIN
        // ========================================================

        if ("APPLICANT".equalsIgnoreCase(request.getRole())) {

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                user = userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElse(null);

            } else if (request.getPhone() != null &&
                    !request.getPhone().isBlank()) {

                user = userRepository
                        .findByPhone(
                                request.getPhone()
                        )
                        .orElse(null);
            }
        }


        // ========================================================
        // CONTRACTOR LOGIN
        // ========================================================

        else if ("CONTRACTOR".equalsIgnoreCase(request.getRole())) {

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                user = userRepository
                        .findByEmail(
                                request.getEmail()
                                        .trim()
                                        .toLowerCase()
                        )
                        .orElse(null);
            }
        }


        // ========================================================
        // SUPER ADMIN LOGIN
        // ========================================================

        else if ("SUPER_ADMIN".equalsIgnoreCase(request.getRole())) {

            // ----------------------------------------------------
            // SUPER ADMIN LOGIN USING FIXED EMAIL
            // ----------------------------------------------------
            // Database ID is NOT used.
            // ----------------------------------------------------

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                user = userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElse(null);
            }
        }


        // ========================================================
        // USER NOT FOUND
        // ========================================================

        if (user == null) {

            throw new RuntimeException(
                    "Invalid credentials"
            );
        }


        // ========================================================
        // PASSWORD CHECK
        // ========================================================

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid credentials"
            );
        }


        // ========================================================
        // BLOCKED ACCOUNT CHECK
        // ========================================================

        if (user.getStatus() != null &&
                user.getStatus()
                        .equalsIgnoreCase("Blocked")) {

            throw new RuntimeException(
                    "Account is blocked"
            );
        }


        // ========================================================
        // GENERATE JWT
        // ========================================================

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new LoginResponse(
                token,
                user
        );
    }
}