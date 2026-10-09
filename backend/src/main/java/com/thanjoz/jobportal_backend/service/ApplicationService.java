package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.ApplicationResponse;
import com.thanjoz.jobportal_backend.dto.ApplyJobResponse;
import com.thanjoz.jobportal_backend.entity.Application;
import com.thanjoz.jobportal_backend.entity.ApplicationStatus;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.ApplicationRepository;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import com.thanjoz.jobportal_backend.exception.AlreadyAppliedException;
import com.thanjoz.jobportal_backend.dto.InterviewRequest;
import com.thanjoz.jobportal_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.thanjoz.jobportal_backend.entity.Role;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmailService emailService;

    /*
     * Resume upload directory comes from application.properties
     */
    @Value("${app.upload.resume-dir}")
    private String resumeUploadDirectory;


    // ============================================================
    // APPLY FOR JOB
    // ============================================================

    public ApplyJobResponse applyJob(
            Long jobId,
            String email,
            String name,
            String applicantEmail,
            String phone,
            String qualification,
            String experience,
            MultipartFile resume) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));


        // --------------------------------------------------------
        // ONLY APPLICANT CAN APPLY FOR JOB
        // --------------------------------------------------------

        if (user.getRole() != Role.APPLICANT) {

            throw new IllegalStateException(
                    "Only applicants can apply for jobs"
            );
        }


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));


        // --------------------------------------------------------
        // FIND CONTRACTOR WHO POSTED THIS JOB
        // --------------------------------------------------------

        if (job.getContractorId() == null ||
                job.getContractorId().isBlank()) {

            throw new IllegalStateException(
                    "Contractor information is not available for this job"
            );
        }


        User contractor = userRepository
                .findByContractorId(job.getContractorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Contractor not found"
                        )
                );


        String contractorPhone = contractor.getPhone();


        if (applicationRepository.existsByUserIdAndJobId(
                user.getId(),
                job.getId())) {

            throw new AlreadyAppliedException(
                    "You have already applied for this job");
        }


        Application application = new Application();

        application.setUserId(user.getId());
        application.setJobId(job.getId());
        application.setStatus(ApplicationStatus.APPLIED);


        application.setName(name);
        application.setEmail(applicantEmail);
        application.setPhone(phone);
        application.setQualification(qualification);
        application.setExperience(experience);


        // --------------------------------------------------------
        // SAVE RESUME FILE
        // --------------------------------------------------------

        if (resume != null && !resume.isEmpty()) {

            try {

                Path uploadDirectory =
                        Paths.get(resumeUploadDirectory)
                                .toAbsolutePath()
                                .normalize();

                Files.createDirectories(uploadDirectory);


                String originalFileName =
                        resume.getOriginalFilename();

                if (originalFileName == null ||
                        originalFileName.isBlank()) {

                    throw new RuntimeException(
                            "Invalid resume filename");
                }


                originalFileName =
                        Paths.get(originalFileName)
                                .getFileName()
                                .toString();


                String fileName =
                        UUID.randomUUID()
                                + "_"
                                + originalFileName;


                Path filePath =
                        uploadDirectory.resolve(fileName)
                                .normalize();


                if (!filePath.startsWith(uploadDirectory)) {

                    throw new RuntimeException(
                            "Invalid resume file path");
                }


                Files.copy(
                        resume.getInputStream(),
                        filePath,
                        StandardCopyOption.REPLACE_EXISTING
                );


                application.setResume(fileName);


                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "RESUME SAVED SUCCESSFULLY"
                );

                System.out.println(
                        "File Name: " + fileName
                );

                System.out.println(
                        "File Location: "
                                + filePath.toAbsolutePath()
                );

                System.out.println(
                        "======================================"
                );


            } catch (IOException e) {

                throw new RuntimeException(
                        "Failed to save resume file",
                        e
                );
            }

        } else {

            application.setResume("");
        }


        // --------------------------------------------------------
        // SAVE APPLICATION
        // --------------------------------------------------------

        Application savedApplication =
                applicationRepository.save(application);


        // --------------------------------------------------------
        // SEND APPLICATION EMAIL
        // --------------------------------------------------------

        String companyName = job.getCompany();

        emailService.sendApplicationEmail(
                applicantEmail,
                job.getTitle(),
                companyName,
                contractorPhone


        );


        // --------------------------------------------------------
        // RETURN SUCCESS MESSAGE + CONTRACTOR PHONE
        // --------------------------------------------------------

        return new ApplyJobResponse(
                "Job applied successfully",
                contractorPhone,
                savedApplication.getId()
        );
    }


    // ============================================================
    // GET APPLICATIONS OF ONE USER - PAGINATED
    // ============================================================

    public Page<ApplicationResponse> getApplicationsByUser(
            Long userId,
            Pageable pageable,
            String loggedInEmail) {

        // --------------------------------------------------------
        // FIND LOGGED-IN USER
        // --------------------------------------------------------

        User loggedInUser =
                userRepository.findByEmail(loggedInEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));


        // --------------------------------------------------------
        // SUPER ADMIN CAN VIEW ANY USER'S APPLICATIONS
        // --------------------------------------------------------

        if (loggedInUser.getRole() != Role.SUPER_ADMIN) {

            // ----------------------------------------------------
            // ONLY THE SAME APPLICANT CAN VIEW THEIR APPLICATIONS
            // ----------------------------------------------------

            if (loggedInUser.getRole() != Role.APPLICANT ||
                    !loggedInUser.getId().equals(userId)) {

                throw new IllegalStateException(
                        "You are not authorized to view these applications"
                );
            }
        }


        Page<Application> applications =
                applicationRepository.findByUserId(
                        userId,
                        pageable
                );


        return applications.map(application -> {

            Job job =
                    jobRepository.findById(
                            application.getJobId()
                    ).orElse(null);


            ApplicationResponse response =
                    new ApplicationResponse();


            response.setId(application.getId());
            response.setUserId(application.getUserId());
            response.setJobId(application.getJobId());
            response.setStatus(application.getStatus());


            // Applicant details

            response.setName(
                    application.getName()
            );

            response.setEmail(
                    application.getEmail()
            );

            response.setPhone(
                    application.getPhone()
            );

            response.setQualification(
                    application.getQualification()
            );

            response.setExperience(
                    application.getExperience()
            );

            response.setResume(
                    application.getResume()
            );


            // Job details

            if (job != null) {

                response.setJobTitle(
                        job.getTitle()
                );

                response.setCompany(
                        job.getCompany()
                );

                response.setLocation(
                        job.getLocation()
                );
            }


            return response;
        });
    }


    // ============================================================
    // GET ALL APPLICATIONS FOR ONE JOB - PAGINATED
    // ============================================================

    public Page<Application> getApplicationsByJob(
            Long jobId,
            Pageable pageable,
            String loggedInEmail) {

        // --------------------------------------------------------
        // FIND LOGGED-IN USER
        // --------------------------------------------------------

        User loggedInUser =
                userRepository.findByEmail(loggedInEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));


        // --------------------------------------------------------
        // FIND JOB
        // --------------------------------------------------------

        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job not found"
                                ));


        // --------------------------------------------------------
        // SUPER ADMIN CAN VIEW ANY JOB APPLICATIONS
        // --------------------------------------------------------

        if (loggedInUser.getRole() != Role.SUPER_ADMIN) {

            // ----------------------------------------------------
            // ONLY CONTRACTOR CAN VIEW JOB APPLICATIONS
            // ----------------------------------------------------

            if (loggedInUser.getRole() != Role.CONTRACTOR) {

                throw new IllegalStateException(
                        "You are not authorized to view job applications"
                );
            }


            // ----------------------------------------------------
            // CONTRACTOR MUST OWN THE JOB
            // ----------------------------------------------------

            if (job.getContractorId() == null ||
                    loggedInUser.getContractorId() == null ||
                    !job.getContractorId()
                            .equals(loggedInUser.getContractorId())) {

                throw new IllegalStateException(
                        "You are not authorized to view applications for this job"
                );
            }
        }


        return applicationRepository.findByJobId(
                jobId,
                pageable
        );
    }


    // ============================================================
    // GET APPLICATIONS FOR JOBS POSTED BY CONTRACTOR
    // PAGINATED AT APPLICATION LEVEL
    // ============================================================

    public Page<ApplicationResponse> getApplicationsByContractor(
            String contractorId,
            Pageable pageable,
            String loggedInEmail) {

        // --------------------------------------------------------
        // FIND LOGGED-IN USER
        // --------------------------------------------------------

        User loggedInUser =
                userRepository.findByEmail(loggedInEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));


        // --------------------------------------------------------
        // SUPER ADMIN CAN VIEW ANY CONTRACTOR'S APPLICATIONS
        // --------------------------------------------------------

        if (loggedInUser.getRole() != Role.SUPER_ADMIN) {

            // ----------------------------------------------------
            // ONLY CONTRACTOR CAN ACCESS THIS API
            // ----------------------------------------------------

            if (loggedInUser.getRole() != Role.CONTRACTOR) {

                throw new IllegalStateException(
                        "You are not authorized to view contractor applications"
                );
            }


            // ----------------------------------------------------
            // CONTRACTOR CAN ONLY VIEW THEIR OWN APPLICATIONS
            // ----------------------------------------------------

            if (loggedInUser.getContractorId() == null ||
                    contractorId == null ||
                    !loggedInUser.getContractorId()
                            .equals(contractorId)) {

                throw new IllegalStateException(
                        "You are not authorized to view these applications"
                );
            }
        }


        /*
         * IMPORTANT:
         *
         * contractorId belongs to JOB.
         *
         * Therefore we MUST use JobRepository here.
         *
         * Do NOT use:
         *
         * applicationRepository.findByContractorId(...)
         */


        // --------------------------------------------------------
        // FIND ALL JOBS BELONGING TO THIS CONTRACTOR
        // --------------------------------------------------------

        List<Job> jobs =
                jobRepository.findByContractorId(contractorId);


        // --------------------------------------------------------
        // IF CONTRACTOR HAS NO JOBS
        // --------------------------------------------------------

        if (jobs.isEmpty()) {

            return Page.empty(pageable);
        }


        // --------------------------------------------------------
        // EXTRACT JOB IDS
        // --------------------------------------------------------

        List<Long> jobIds =
                jobs.stream()
                        .map(Job::getId)
                        .toList();


        // --------------------------------------------------------
        // GET APPLICATIONS USING REAL PAGINATION
        // --------------------------------------------------------

        Page<Application> applications =
                applicationRepository.findByJobIdIn(
                        jobIds,
                        pageable
                );


        // --------------------------------------------------------
        // CONVERT APPLICATION -> APPLICATION RESPONSE
        // --------------------------------------------------------

        return applications.map(application -> {

            Job job =
                    jobs.stream()
                            .filter(j ->
                                    j.getId().equals(
                                            application.getJobId()
                                    ))
                            .findFirst()
                            .orElse(null);


            ApplicationResponse response =
                    new ApplicationResponse();


            // ----------------------------------------------------
            // APPLICATION DETAILS
            // ----------------------------------------------------

            response.setId(
                    application.getId()
            );

            response.setUserId(
                    application.getUserId()
            );

            response.setJobId(
                    application.getJobId()
            );

            response.setStatus(
                    application.getStatus()
            );


            // ----------------------------------------------------
            // APPLICANT DETAILS
            // ----------------------------------------------------

            response.setName(
                    application.getName()
            );

            response.setEmail(
                    application.getEmail()
            );

            response.setPhone(
                    application.getPhone()
            );

            response.setQualification(
                    application.getQualification()
            );

            response.setExperience(
                    application.getExperience()
            );

            response.setResume(
                    application.getResume()
            );


            // ----------------------------------------------------
            // JOB DETAILS
            // ----------------------------------------------------

            if (job != null) {

                response.setJobTitle(
                        job.getTitle()
                );

                response.setCompany(
                        job.getCompany()
                );

                response.setLocation(
                        job.getLocation()
                );
            }


            return response;
        });
    }


    // ============================================================
    // UPDATE APPLICATION STATUS
    // ============================================================

    public Application updateApplicationStatus(
            Long applicationId,
            ApplicationStatus newStatus,
            String contractorEmail) {

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found"
                                ));


        User contractor =
                userRepository.findByEmail(contractorEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Contractor not found"
                                ));


        Job job =
                jobRepository.findById(
                        application.getJobId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found"
                        ));


        if (job.getContractorId() == null ||
                contractor.getContractorId() == null ||
                !job.getContractorId()
                        .equals(contractor.getContractorId())) {

            throw new IllegalStateException(
                    "You are not authorized to manage this application"
            );
        }


        ApplicationStatus currentStatus =
                application.getStatus();

        boolean validTransition = false;


        switch (currentStatus) {

            case APPLIED:

                if (newStatus == ApplicationStatus.RESUME_REVIEWED ||
                        newStatus == ApplicationStatus.REJECTED) {

                    validTransition = true;
                }

                break;


            case RESUME_REVIEWED:

                if (newStatus == ApplicationStatus.SHORTLISTED ||
                        newStatus == ApplicationStatus.REJECTED) {

                    validTransition = true;
                }

                break;


            case SHORTLISTED:

                if (newStatus ==
                        ApplicationStatus.INTERVIEW_SCHEDULED ||
                        newStatus == ApplicationStatus.REJECTED) {

                    validTransition = true;
                }

                break;


            case INTERVIEW_SCHEDULED:

                if (newStatus == ApplicationStatus.SELECTED ||
                        newStatus == ApplicationStatus.REJECTED) {

                    validTransition = true;
                }

                break;


            case SELECTED:
            case REJECTED:

                validTransition = false;

                break;
        }


        if (!validTransition) {

            throw new IllegalStateException(
                    "Invalid application status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }


        application.setStatus(newStatus);


        Application savedApplication =
                applicationRepository.save(application);


        if (newStatus == ApplicationStatus.SELECTED) {

            emailService.sendApplicationSelectedEmail(
                    application.getEmail(),
                    job.getTitle(),
                    job.getCompany()
            );

        } else if (newStatus == ApplicationStatus.REJECTED) {

            emailService.sendApplicationRejectedEmail(
                    application.getEmail(),
                    job.getTitle(),
                    job.getCompany()
            );
        }


        return savedApplication;
    }


    // ============================================================
    // SCHEDULE INTERVIEW
    // ============================================================

    public Application scheduleInterview(
            Long applicationId,
            InterviewRequest request,
            String contractorEmail) {

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found"
                                ));


        User contractor =
                userRepository.findByEmail(contractorEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Contractor not found"
                                ));


        Job job =
                jobRepository.findById(
                        application.getJobId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found"
                        ));


        if (job.getContractorId() == null ||
                contractor.getContractorId() == null ||
                !job.getContractorId()
                        .equals(contractor.getContractorId())) {

            throw new IllegalStateException(
                    "You are not authorized to schedule an interview for this application"
            );
        }


        if (application.getStatus() !=
                ApplicationStatus.SHORTLISTED) {

            throw new IllegalStateException(
                    "Only shortlisted applicants can be scheduled for an interview"
            );
        }


        if (request.getDate() == null) {

            throw new IllegalStateException(
                    "Interview date is required"
            );
        }


        if (request.getTime() == null) {

            throw new IllegalStateException(
                    "Interview time is required"
            );
        }


        if (request.getMode() == null ||
                request.getMode().isBlank()) {

            throw new IllegalStateException(
                    "Interview mode is required"
            );
        }


        application.setInterviewDate(
                request.getDate()
        );

        application.setInterviewTime(
                request.getTime()
        );

        application.setInterviewMode(
                request.getMode()
        );

        application.setMeetingLink(
                request.getMeetingLink()
        );

        application.setInterviewMessage(
                request.getMessage()
        );


        application.setStatus(
                ApplicationStatus.INTERVIEW_SCHEDULED
        );


        Application savedApplication =
                applicationRepository.save(application);


        emailService.sendInterviewScheduledEmail(
                application.getEmail(),
                job.getTitle(),
                job.getCompany(),
                request.getDate().toString(),
                request.getTime().toString(),
                request.getMode(),
                request.getMeetingLink(),
                request.getMessage()
        );


        return savedApplication;
    }


    // ============================================================
    // VERIFY RESUME ACCESS
    // ============================================================

    public void verifyResumeAccess(
            String fileName,
            String userEmail) {

        Application application =
                applicationRepository.findByResume(fileName)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found"
                                ));


        User user =
                userRepository.findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));


        // SUPER ADMIN CAN VIEW ANY RESUME

        if (user.getRole() == Role.SUPER_ADMIN) {
            return;
        }


        Job job =
                jobRepository.findById(
                        application.getJobId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found"
                        ));


        // CONTRACTOR CAN VIEW RESUME ONLY FOR THEIR OWN JOB

        if (user.getRole() == Role.CONTRACTOR &&
                job.getContractorId() != null &&
                job.getContractorId()
                        .equals(user.getContractorId())) {

            return;
        }


        throw new IllegalStateException(
                "You are not authorized to view this resume"
        );
    }
}