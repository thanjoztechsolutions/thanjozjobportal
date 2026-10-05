package com.thanjoz.jobportal_backend.service;

import com.thanjoz.jobportal_backend.entity.Job;
import com.thanjoz.jobportal_backend.entity.SavedJob;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.JobRepository;
import com.thanjoz.jobportal_backend.repository.SavedJobRepository;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import com.thanjoz.jobportal_backend.exception.ResourceNotFoundException;
import com.thanjoz.jobportal_backend.exception.AlreadySavedJobException;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public SavedJobService(
            SavedJobRepository savedJobRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {

        this.savedJobRepository = savedJobRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public SavedJob saveJob(Long userId, Long jobId) {

        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new AlreadySavedJobException("Job already saved");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);

        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getSavedJobs(Long userId) {
        return savedJobRepository.findByUserId(userId);
    }

    public void removeSavedJob(Long userId, Long jobId) {

        SavedJob savedJob = savedJobRepository
                .findByUserIdAndJobId(userId, jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved job not found"));

        savedJobRepository.delete(savedJob);
    }
}