package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "companies")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
