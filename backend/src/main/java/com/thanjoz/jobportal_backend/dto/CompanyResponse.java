package com.thanjoz.jobportal_backend.dto;

import lombok.Data;

@Data
public class CompanyResponse {

    private Long id;

    private String name;

    private String description;

    private String website;

    private Long ownerId;

}