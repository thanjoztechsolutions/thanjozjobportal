package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.LoginRequest;
import com.thanjoz.jobportal_backend.dto.LoginResponse;
import com.thanjoz.jobportal_backend.dto.RegisterRequest;
import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.security.JwtService;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        if (request.getPhone() != null &&
                !request.getPhone().isBlank() &&
                userRepository.existsByPhone(request.getPhone())) {

            return "Phone number already registered";
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        if ("APPLICANT".equalsIgnoreCase(request.getRole())) {

            user.setRole(Role.APPLICANT);

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setResume(request.getResume());

            user.setStatus("Active");

        } else if ("CONTRACTOR".equalsIgnoreCase(request.getRole())) {

            user.setRole(Role.CONTRACTOR);

            user.setCompanyName(request.getCompanyName());
            user.setUsername(request.getUsername());
            user.setAddress(request.getAddress());
            user.setGst(request.getGst());
            user.setCompanyLicense(request.getCompanyLicense());

            // Generate Contractor ID
            long contractorCount =
                    userRepository.count() + 1000;

            user.setContractorId("CTR" + contractorCount);

            user.setStatus("Active");

        } else {

            return "Invalid role";
        }

        userRepository.save(user);

        return "Registration successful";
    }


    public LoginResponse loginUser(LoginRequest request) {

        User user = null;

        if ("APPLICANT".equalsIgnoreCase(request.getRole())) {

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                user = userRepository
                        .findByEmail(request.getEmail())
                        .orElse(null);

            } else if (request.getPhone() != null &&
                    !request.getPhone().isBlank()) {

                user = userRepository
                        .findByPhone(request.getPhone())
                        .orElse(null);
            }

        } else if ("CONTRACTOR".equalsIgnoreCase(request.getRole())) {

            if (request.getContractorId() != null &&
                    request.getUsername() != null) {

                user = userRepository
                        .findByContractorId(request.getContractorId())
                        .orElse(null);

                if (user != null &&
                        !user.getUsername().equals(request.getUsername())) {

                    user = null;
                }
            }

        } else if ("SUPER_ADMIN".equalsIgnoreCase(request.getRole())) {

            if (request.getAdminId() != null) {

                try {

                    Long id = Long.parseLong(request.getAdminId());

                    user = userRepository
                            .findById(id)
                            .orElse(null);

                } catch (NumberFormatException e) {

                    user = null;
                }
            }
        }

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        if (user.getStatus() != null &&
                user.getStatus().equalsIgnoreCase("Blocked")) {

            throw new RuntimeException("Account is blocked");
        }

        // Generate JWT token after successful login
        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getId()
        );
    }
}