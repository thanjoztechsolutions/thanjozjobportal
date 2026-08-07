package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private String salary;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private JobCategory category;

}