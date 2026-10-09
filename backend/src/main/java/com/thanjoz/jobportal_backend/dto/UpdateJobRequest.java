package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class UpdateJobRequest {

    private String title;

    private String description;

    private String company;

    private String location;

    private String salary;

    private String type;

    private String experience;

    private String category;

    private Integer vacancies;

    private String requiredSkill;
}