package com.thanjoz.jobportal_backend.security;


import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


import java.util.Collections;



@Service
public class CustomUserDetailsService implements UserDetailsService {



    @Autowired
    private UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {



        User user = userRepository
                .findByEmailOrPhoneNumber(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );



        return new org.springframework.security.core.userdetails.User(


                user.getEmail(),


                user.getPassword(),


                // Temporary role for JWT authentication
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().name())
                )


        );

    }


}