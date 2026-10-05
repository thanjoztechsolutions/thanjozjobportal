package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String company;

    private String location;

    private String salary;

    private String type;

    private String experience;

    private Integer vacancies;

    private String requiredSkill;

    // Contractor who posted this job
    private String contractorId;

    // Job category name
    private String category;
}