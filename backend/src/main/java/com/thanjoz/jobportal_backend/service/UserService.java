package com.thanjoz.jobportal_backend.service;


import com.thanjoz.jobportal_backend.dto.*;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.enums.Role;
import com.thanjoz.jobportal_backend.jwt.JwtService;
import com.thanjoz.jobportal_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;



    // REGISTER

    public String registerUser(RegisterRequest request) {


        if(userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already exists");

        }


        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );


        user.setRole(Role.ROLE_USER);


        userRepository.save(user);


        return "User Registered Successfully";

    }




    // LOGIN

    public LoginResponse login(LoginRequest request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid password");

        }


        String token =
                jwtService.generateToken(user.getEmail());


        LoginResponse response = new LoginResponse();

        response.setToken(token);
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());


        return response;

    }




    // GET PROFILE

    public User getProfile(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

    }





    // UPDATE PROFILE

    public String updateProfile(UpdateProfileRequest request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());


        userRepository.save(user);


        return "Profile Updated Successfully";

    }




    // CHANGE PASSWORD

    public String changePassword(ChangePasswordRequest request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        if(!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Old password incorrect");

        }


        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );


        userRepository.save(user);


        return "Password Changed Successfully";

    }




    // UPDATE USER

    public String updateUser(Long id, RegisterRequest request) {


        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());


        userRepository.save(user);


        return "User Updated Successfully";

    }




    // DELETE USER

    public String deleteUser(Long id) {


        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        userRepository.delete(user);


        return "User Deleted Successfully";

    }




    // UPDATE ROLE

    public String updateRole(Long id, RoleUpdateRequest request) {


        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        user.setRole(
                Role.valueOf(request.getRole())
        );


        userRepository.save(user);


        return "Role Updated Successfully";

    }





    // GET ALL USERS

    public List<UserResponse> getAllUsers() {


        return userRepository.findAll()
                .stream()
                .map(user -> {

                    UserResponse response =
                            new UserResponse();


                    response.setId(user.getId());

                    response.setFirstName(
                            user.getFirstName()
                    );

                    response.setLastName(
                            user.getLastName()
                    );

                    response.setEmail(
                            user.getEmail()
                    );

                    response.setPhoneNumber(
                            user.getPhoneNumber()
                    );


                    // IMPORTANT FIX
                    response.setRole(
                            user.getRole()
                    );


                    return response;

                })
                .collect(Collectors.toList());

    }


}