package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.dto.SavedJobRequest;
import com.thanjoz.jobportal_backend.dto.SavedJobResponse;
import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.SavedJob;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.SavedJobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;


    // ================= SAVE JOB =================

    public SavedJobResponse saveJob(
            Long jobId,
            SavedJobRequest request
    ) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );

        if (savedJobRepository.existsByUserIdAndJobId(
                user.getId(),
                job.getId()
        )) {

            throw new RuntimeException("Job already saved");

        }

        SavedJob savedJob = new SavedJob();

        savedJob.setUser(user);
        savedJob.setJob(job);

        SavedJob saved =
                savedJobRepository.save(savedJob);

        SavedJobResponse response =
                new SavedJobResponse();

        response.setId(saved.getId());
        response.setUserId(user.getId());
        response.setUserName(
                user.getFirstName() + " " + user.getLastName()
        );
        response.setJobId(job.getId());
        response.setJobTitle(job.getTitle());

        return response;
    }


    // ================= GET SAVED JOBS =================

    public List<SavedJobResponse> getSavedJobs(Long userId) {

        return savedJobRepository.findByUserId(userId)
                .stream()
                .map(savedJob -> {

                    SavedJobResponse response =
                            new SavedJobResponse();

                    response.setId(savedJob.getId());

                    response.setUserId(
                            savedJob.getUser().getId()
                    );

                    response.setUserName(
                            savedJob.getUser().getFirstName()
                                    + " "
                                    + savedJob.getUser().getLastName()
                    );

                    response.setJobId(
                            savedJob.getJob().getId()
                    );

                    response.setJobTitle(
                            savedJob.getJob().getTitle()
                    );

                    return response;

                })
                .toList();
    }


    // ================= REMOVE SAVED JOB =================

    @Transactional
    public String removeSavedJob(
            Long userId,
            Long jobId
    ) {

        savedJobRepository.deleteByUserIdAndJobId(
                userId,
                jobId
        );

        return "Saved job removed successfully";
    }

}