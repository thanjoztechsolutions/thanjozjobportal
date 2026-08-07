package com.thanjoz.jobportal_backend.entity;

import com.thanjoz.jobportal_backend.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;


    private String email;


    private String phoneNumber;


    private String password;


    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;



    // One user can own many companies

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL)
    private List<Company> companies;


}