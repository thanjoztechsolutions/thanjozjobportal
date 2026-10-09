package com.thanjoz.jobportal_backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    // ============================================================
    // CONTRACTOR REGISTRATION EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendRegistrationEmail(
            String toEmail,
            String contractorId) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Thanjoz Job Portal - Registration Successful"
            );

            message.setText(
                    "Dear Contractor,\n\n" +
                            "Your registration with Thanjoz Job Portal was successful.\n\n" +
                            "Your Contractor ID is: " + contractorId + "\n\n" +
                            "Please keep this Contractor ID safe. " +
                            "You will need it to log in to your Contractor account.\n\n" +
                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Registration email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Registration email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // JOB APPLICATION EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendApplicationEmail(
            String toEmail,
            String jobTitle,
            String companyName,
            String contractorPhone) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Job Application Successful - Thanjoz Job Portal"
            );

            message.setText(
                    "Dear Applicant,\n\n" +
                            "Your job application has been successfully submitted.\n\n" +
                            "Job: " + jobTitle + "\n" +
                            "Company: " + companyName + "\n" +
                            "Contractor Phone: " +
                            (contractorPhone != null &&
                                    !contractorPhone.isBlank()
                                    ? contractorPhone
                                    : "Not provided") +
                            "\n\n" +
                            "You have successfully applied for this position.\n\n" +
                            "You may contact the contractor using the phone number " +
                            "provided above regarding the job application.\n\n" +
                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Application email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Application email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // APPLICANT REGISTRATION EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendApplicantRegistrationEmail(
            String toEmail) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Thanjoz Job Portal - Registration Successful"
            );

            message.setText(
                    "Dear Applicant,\n\n" +
                            "Your registration with Thanjoz Job Portal was successful.\n\n" +
                            "You can now log in to your account and start exploring job opportunities.\n\n" +
                            "Thank you for joining Thanjoz Job Portal.\n\n" +
                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Applicant registration email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Applicant registration email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // INTERVIEW SCHEDULED EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendInterviewScheduledEmail(
            String toEmail,
            String jobTitle,
            String companyName,
            String interviewDate,
            String interviewTime,
            String interviewMode,
            String meetingLink,
            String interviewMessage) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Interview Scheduled - Thanjoz Job Portal"
            );

            message.setText(
                    "Dear Applicant,\n\n" +

                            "Congratulations! You have been shortlisted " +
                            "for the next stage of the hiring process.\n\n" +

                            "Your interview has been scheduled.\n\n" +

                            "Job: " + jobTitle + "\n" +
                            "Company: " + companyName + "\n\n" +

                            "Interview Date: " + interviewDate + "\n" +
                            "Interview Time: " + interviewTime + "\n" +
                            "Interview Mode: " + interviewMode + "\n" +

                            "Meeting Link: " +
                            (meetingLink != null &&
                                    !meetingLink.isBlank()
                                    ? meetingLink
                                    : "Not provided") +
                            "\n\n" +

                            "Message from Contractor:\n" +
                            (interviewMessage != null &&
                                    !interviewMessage.isBlank()
                                    ? interviewMessage
                                    : "No additional message") +
                            "\n\n" +

                            "Please make sure you are available at the scheduled time.\n\n" +

                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Interview scheduled email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Interview scheduled email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // APPLICATION SELECTED EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendApplicationSelectedEmail(
            String toEmail,
            String jobTitle,
            String companyName) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Congratulations! You Have Been Selected - Thanjoz Job Portal"
            );

            message.setText(
                    "Dear Applicant,\n\n" +

                            "Congratulations!\n\n" +

                            "We are pleased to inform you that you have been " +
                            "selected for the position.\n\n" +

                            "Job: " + jobTitle + "\n" +
                            "Company: " + companyName + "\n\n" +

                            "The contractor has selected you for this position.\n\n" +

                            "Please follow any further instructions provided " +
                            "by the contractor.\n\n" +

                            "Congratulations once again!\n\n" +

                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Application selected email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Application selected email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // APPLICATION REJECTED EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendApplicationRejectedEmail(
            String toEmail,
            String jobTitle,
            String companyName) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Application Update - Thanjoz Job Portal"
            );

            message.setText(
                    "Dear Applicant,\n\n" +

                            "Thank you for your interest in the position.\n\n" +

                            "After reviewing your application, " +
                            "the contractor has decided not to proceed " +
                            "with your application at this time.\n\n" +

                            "Job: " + jobTitle + "\n" +
                            "Company: " + companyName + "\n\n" +

                            "We appreciate the time and effort you invested " +
                            "in the application process and encourage you " +
                            "to explore other opportunities on Thanjoz Job Portal.\n\n" +

                            "We wish you all the best in your job search.\n\n" +

                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Application rejected email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Application rejected email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // CONTRACTOR REQUEST APPROVED EMAIL
    // ============================================================

    @Async("emailTaskExecutor")
    public void sendContractorApprovalEmail(
            String toEmail,
            String fullName,
            String contractorId) {

        try {

            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setTo(toEmail);

            message.setSubject(
                    "Thanjoz Job Portal - Contractor Registration Approved"
            );

            message.setText(
                    "Dear " + fullName + ",\n\n" +

                            "Your contractor registration request " +
                            "has been approved by the Thanjoz Job Portal " +
                            "Super Admin.\n\n" +

                            "Your Contractor ID is: " +
                            contractorId + "\n\n" +

                            "Please keep your Contractor ID safe. " +
                            "You will need it to log in to your Contractor account.\n\n" +

                            "You can now complete your contractor registration " +
                            "using your approved email address.\n\n" +

                            "Thank you,\n" +
                            "Thanjoz Job Portal Team"
            );

            mailSender.send(message);

            System.out.println(
                    "Contractor approval email sent successfully to: "
                            + toEmail
            );

        } catch (Exception e) {

            System.err.println(
                    "Contractor approval email failed for "
                            + toEmail + ": "
                            + e.getMessage()
            );
        }
    }
}