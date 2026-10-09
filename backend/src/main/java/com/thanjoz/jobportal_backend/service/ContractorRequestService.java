package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.CompleteContractorRegistrationRequest;
import com.thanjoz.jobportal_backend.entity.ContractorRegistrationRequest;
import com.thanjoz.jobportal_backend.entity.ContractorRequestStatus;
import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.ContractorRequestRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContractorRequestService {

    private final ContractorRequestRepository contractorRequestRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ContractorRequestService(
            ContractorRequestRepository contractorRequestRepository,
            UserRepository userRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {

        this.contractorRequestRepository =
                contractorRequestRepository;

        this.userRepository =
                userRepository;

        this.emailService =
                emailService;

        this.passwordEncoder =
                passwordEncoder;
    }


    // ============================================================
    // CREATE CONTRACTOR REGISTRATION REQUEST
    // ============================================================

    public ContractorRegistrationRequest createRequest(
            ContractorRegistrationRequest request) {

        // --------------------------------------------------------
        // NORMALIZE EMAIL
        // --------------------------------------------------------

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        request.setEmail(email);


        // --------------------------------------------------------
        // CHECK ACTIVE USER WITH SAME EMAIL
        // --------------------------------------------------------

        if (userRepository.existsByEmail(email)) {

            throw new IllegalStateException(
                    "A user with this email already exists"
            );
        }


        // --------------------------------------------------------
        // CHECK PENDING CONTRACTOR REQUEST
        // --------------------------------------------------------

        if (contractorRequestRepository
                .existsByEmailAndStatus(
                        email,
                        ContractorRequestStatus.PENDING
                )) {

            throw new IllegalStateException(
                    "A contractor request is already pending for this email"
            );
        }


        // --------------------------------------------------------
        // SET STATUS
        // --------------------------------------------------------

        request.setStatus(
                ContractorRequestStatus.PENDING
        );


        // --------------------------------------------------------
        // SET TIMESTAMPS
        // --------------------------------------------------------

        LocalDateTime now =
                LocalDateTime.now();

        request.setCreatedAt(now);
        request.setUpdatedAt(now);


        // --------------------------------------------------------
        // SAVE REQUEST
        // --------------------------------------------------------

        return contractorRequestRepository.save(request);
    }


    // ============================================================
    // GET ALL CONTRACTOR REQUESTS
    // ============================================================

    public List<ContractorRegistrationRequest> getAllRequests() {

        return contractorRequestRepository
                .findAllByOrderByIdDesc();
    }


    // ============================================================
    // GET ONE CONTRACTOR REQUEST
    // ============================================================

    public ContractorRegistrationRequest getRequestById(
            Long id) {

        return contractorRequestRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Contractor request not found"
                        )
                );
    }


    // ============================================================
    // APPROVE CONTRACTOR REQUEST
    // ============================================================
    //
    // IMPORTANT:
    //
    // Approval DOES NOT create the User account.
    //
    // Approval only:
    //
    // 1. Checks the request
    // 2. Generates Contractor ID
    // 3. Stores Contractor ID
    // 4. Changes status to APPROVED
    // 5. Sends approval email
    //
    // User account is created later during
    // completeRegistration().
    // ============================================================

    @Transactional
    public ContractorRegistrationRequest approveRequest(
            Long id) {

        ContractorRegistrationRequest request =
                getRequestById(id);


        // --------------------------------------------------------
        // ONLY PENDING REQUEST CAN BE APPROVED
        // --------------------------------------------------------

        if (request.getStatus() !=
                ContractorRequestStatus.PENDING) {

            throw new IllegalStateException(
                    "This contractor request has already been processed"
            );
        }


        // --------------------------------------------------------
        // CHECK EMAIL AGAIN
        // --------------------------------------------------------

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new IllegalStateException(
                    "A user with this email already exists"
            );
        }


        // --------------------------------------------------------
        // GENERATE CONTRACTOR ID
        // --------------------------------------------------------
        //
        // Example:
        //
        // Request ID = 1
        // Contractor ID = CTR1001
        //
        // Request ID = 25
        // Contractor ID = CTR1025
        // --------------------------------------------------------

        long contractorNumber =
                request.getId() + 1000;

        String contractorId =
                "CTR" + contractorNumber;


        // --------------------------------------------------------
        // CHECK CONTRACTOR ID
        // --------------------------------------------------------

        if (userRepository.existsByContractorId(
                contractorId)) {

            throw new IllegalStateException(
                    "Contractor ID already exists"
            );
        }


        // --------------------------------------------------------
        // STORE CONTRACTOR ID IN REQUEST
        // --------------------------------------------------------

        request.setContractorId(
                contractorId
        );


        // --------------------------------------------------------
        // CHANGE STATUS TO APPROVED
        // --------------------------------------------------------

        request.setStatus(
                ContractorRequestStatus.APPROVED
        );


        // --------------------------------------------------------
        // UPDATE TIMESTAMP
        // --------------------------------------------------------

        request.setUpdatedAt(
                LocalDateTime.now()
        );


        // --------------------------------------------------------
        // SAVE APPROVED REQUEST
        // --------------------------------------------------------

        ContractorRegistrationRequest savedRequest =
                contractorRequestRepository.save(request);


        // --------------------------------------------------------
        // SEND APPROVAL EMAIL
        // --------------------------------------------------------

        emailService.sendContractorApprovalEmail(
                savedRequest.getEmail(),
                savedRequest.getFullName(),
                savedRequest.getContractorId()
        );


        return savedRequest;
    }


    // ============================================================
    // REJECT CONTRACTOR REQUEST
    // ============================================================

    @Transactional
    public ContractorRegistrationRequest rejectRequest(
            Long id,
            String reason) {

        ContractorRegistrationRequest request =
                getRequestById(id);


        // --------------------------------------------------------
        // ONLY PENDING REQUEST CAN BE REJECTED
        // --------------------------------------------------------

        if (request.getStatus() !=
                ContractorRequestStatus.PENDING) {

            throw new IllegalStateException(
                    "This contractor request has already been processed"
            );
        }


        // --------------------------------------------------------
        // CHANGE STATUS
        // --------------------------------------------------------

        request.setStatus(
                ContractorRequestStatus.REJECTED
        );


        // --------------------------------------------------------
        // SAVE REJECTION REASON
        // --------------------------------------------------------

        if (reason != null &&
                !reason.isBlank()) {

            request.setRejectionReason(
                    reason.trim()
            );
        }


        // --------------------------------------------------------
        // UPDATE TIMESTAMP
        // --------------------------------------------------------

        request.setUpdatedAt(
                LocalDateTime.now()
        );


        return contractorRequestRepository.save(
                request
        );
    }


    // ============================================================
    // COMPLETE CONTRACTOR REGISTRATION
    // ============================================================
    //
    // POST:
    //
    // /api/contractor-requests/complete-registration
    //
    // This happens AFTER Super Admin approval.
    //
    // Here we:
    //
    // 1. Find the approved contractor request
    // 2. Verify Contractor ID
    // 3. Verify APPROVED status
    // 4. Validate password
    // 5. Create User
    // 6. BCrypt encode password
    // 7. Set role CONTRACTOR
    // 8. Set status Active
    // 9. Save User
    // 10. Change request status to COMPLETED
    // ============================================================

    @Transactional
    public ContractorRegistrationRequest completeRegistration(
            CompleteContractorRegistrationRequest request) {

        // --------------------------------------------------------
        // VALIDATE EMAIL
        // --------------------------------------------------------

        if (request.getEmail() == null ||
                request.getEmail().isBlank()) {

            throw new IllegalStateException(
                    "Email is required"
            );
        }


        // --------------------------------------------------------
        // NORMALIZE EMAIL
        // --------------------------------------------------------

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();


        // --------------------------------------------------------
        // FIND CONTRACTOR REQUEST
        // --------------------------------------------------------

        ContractorRegistrationRequest contractorRequest =
                contractorRequestRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Contractor registration request not found"
                                )
                        );


        // --------------------------------------------------------
        // CHECK CONTRACTOR ID
        // --------------------------------------------------------

        if (contractorRequest.getContractorId() == null ||
                request.getContractorId() == null ||
                !contractorRequest.getContractorId()
                        .equals(request.getContractorId())) {

            throw new IllegalStateException(
                    "Invalid contractor ID"
            );
        }


        // --------------------------------------------------------
        // CHECK APPROVAL STATUS
        // --------------------------------------------------------

        if (contractorRequest.getStatus() !=
                ContractorRequestStatus.APPROVED) {

            if (contractorRequest.getStatus() ==
                    ContractorRequestStatus.COMPLETED) {

                throw new IllegalStateException(
                        "Contractor registration has already been completed"
                );
            }

            throw new IllegalStateException(
                    "Contractor registration is not approved"
            );
        }


        // --------------------------------------------------------
        // VALIDATE PASSWORD
        // --------------------------------------------------------

        if (request.getPassword() == null ||
                request.getPassword().isBlank()) {

            throw new IllegalStateException(
                    "Password is required"
            );
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE CONTRACTOR ID
        // --------------------------------------------------------

        if (userRepository.existsByContractorId(
                contractorRequest.getContractorId())) {

            throw new IllegalStateException(
                    "Contractor account already exists"
            );
        }


        // --------------------------------------------------------
        // CHECK DUPLICATE EMAIL
        // --------------------------------------------------------

        if (userRepository.existsByEmail(email)) {

            throw new IllegalStateException(
                    "A user with this email already exists"
            );
        }


        // --------------------------------------------------------
        // CREATE USER
        // --------------------------------------------------------

        User user = new User();


        // --------------------------------------------------------
        // CONTRACTOR ID
        // --------------------------------------------------------

        user.setContractorId(
                contractorRequest.getContractorId()
        );


        // --------------------------------------------------------
        // FIRST NAME
        // --------------------------------------------------------

        user.setFirstName(
                request.getFirstName()
        );


        // --------------------------------------------------------
        // LAST NAME
        // --------------------------------------------------------

        user.setLastName(
                request.getLastName()
        );


        // --------------------------------------------------------
        // EMAIL
        // --------------------------------------------------------

        user.setEmail(
                email
        );


        // --------------------------------------------------------
        // USERNAME
        // --------------------------------------------------------

        user.setUsername(
                request.getUsername()
        );


        // --------------------------------------------------------
        // PHONE
        // --------------------------------------------------------

        user.setPhone(
                request.getPhone()
        );


        // --------------------------------------------------------
        // PASSWORD
        // --------------------------------------------------------
        //
        // IMPORTANT:
        //
        // The password coming from the frontend is plain text.
        //
        // We encode it using BCrypt BEFORE saving.
        //
        // Database will contain the BCrypt hash,
        // NOT the original password.
        // --------------------------------------------------------

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        // --------------------------------------------------------
        // ROLE
        // --------------------------------------------------------

        user.setRole(
                Role.CONTRACTOR
        );


        // --------------------------------------------------------
        // COMPANY NAME
        // --------------------------------------------------------

        user.setCompanyName(
                request.getCompanyName()
        );


        // --------------------------------------------------------
        // COMPANY LICENSE
        // --------------------------------------------------------

        user.setCompanyLicense(
                request.getCompanyLicense()
        );


        // --------------------------------------------------------
        // ADDRESS
        // --------------------------------------------------------

        user.setAddress(
                request.getAddress()
        );


        // --------------------------------------------------------
        // GST
        // --------------------------------------------------------

        user.setGst(
                request.getGst()
        );


        // --------------------------------------------------------
        // LOCATION
        // --------------------------------------------------------

        user.setLocation(
                request.getLocation()
        );


        // --------------------------------------------------------
        // QUALIFICATION
        // --------------------------------------------------------

        user.setQualification(
                request.getQualification()
        );


        // --------------------------------------------------------
        // EXPERIENCE
        // --------------------------------------------------------

        user.setExperience(
                request.getExperience()
        );


        // --------------------------------------------------------
        // ACCOUNT STATUS
        // --------------------------------------------------------

        user.setStatus(
                "Active"
        );


        // --------------------------------------------------------
        // JOINED DATE
        // --------------------------------------------------------

        user.setJoined(
                LocalDateTime.now()
        );


        // --------------------------------------------------------
        // SAVE CONTRACTOR USER
        // --------------------------------------------------------

        userRepository.save(user);


        // --------------------------------------------------------
        // MARK REQUEST AS COMPLETED
        // --------------------------------------------------------

        contractorRequest.setStatus(
                ContractorRequestStatus.COMPLETED
        );


        // --------------------------------------------------------
        // UPDATE TIMESTAMP
        // --------------------------------------------------------

        contractorRequest.setUpdatedAt(
                LocalDateTime.now()
        );


        // --------------------------------------------------------
        // SAVE COMPLETED REQUEST
        // --------------------------------------------------------

        return contractorRequestRepository.save(
                contractorRequest
        );
    }
}