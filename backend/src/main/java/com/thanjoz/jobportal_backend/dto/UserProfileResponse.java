package com.thanjoz.jobportal_backend.dto;

import com.thanjoz.jobportal_backend.enums.Role;
import lombok.Data;

@Data
public class UserProfileResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;

}