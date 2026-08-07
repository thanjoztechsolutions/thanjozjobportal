package com.thanjoz.jobportal_backend.controller;

import java.util.List;

import com.thanjoz.jobportal_backend.dto.*;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;



    // ================= REGISTER =================

    @PostMapping("/register")
    public String registerUser(
            @RequestBody RegisterRequest request
    ){

        return userService.registerUser(request);

    }





    // ================= LOGIN =================

    @PostMapping("/login")
    public LoginResponse loginUser(
            @RequestBody LoginRequest request
    ){

        return userService.login(request);

    }




    // ================= GET USER PROFILE BY ID =================

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(
            @PathVariable Long id
    ){

        User user = userService.getProfile(id);

        return ResponseEntity.ok(user);

    }





    // ================= UPDATE PROFILE =================

    @PutMapping("/profile")
    public String updateProfile(
            @RequestBody UpdateProfileRequest request
    ){

        return userService.updateProfile(request);

    }





    // ================= CHANGE PASSWORD =================

    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordRequest request
    ){

        return userService.changePassword(request);

    }





    // ================= UPDATE USER =================

    @PutMapping("/{id}")
    public String updateUser(
            @PathVariable Long id,
            @RequestBody RegisterRequest request
    ){

        return userService.updateUser(id, request);

    }





    // ================= DELETE USER =================

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id
    ){

        return userService.deleteUser(id);

    }





    // ================= UPDATE ROLE =================

    @PutMapping("/{id}/role")
    public String updateRole(
            @PathVariable Long id,
            @RequestBody RoleUpdateRequest request
    ){

        return userService.updateRole(id, request);

    }





    // ================= GET ALL USERS =================

    @GetMapping
    public List<UserResponse> getAllUsers(){

        return userService.getAllUsers();

    }

}