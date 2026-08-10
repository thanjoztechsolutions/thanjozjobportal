package com.thanjoz.jobportal_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractorId;

    private String firstName;

    private String lastName;

    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @JsonIgnore
    private String password;

    private String address;

    private String gst;

    private String companyName;

    private String companyLicense;

    private String resume;

    private String location;

    private String status;

    private LocalDateTime joined;

    @Enumerated(EnumType.STRING)
    private Role role;
}