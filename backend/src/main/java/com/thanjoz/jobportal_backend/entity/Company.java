package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String description;


    private String website;



    // Many companies can belong to one user

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;


}