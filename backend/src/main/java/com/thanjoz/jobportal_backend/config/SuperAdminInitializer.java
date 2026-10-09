package com.thanjoz.jobportal_backend.config;

import com.thanjoz.jobportal_backend.entity.Role;
import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SuperAdminInitializer {

    @Bean
    CommandLineRunner createSuperAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String superAdminEmail =
                    "mathan@thanjoztech.com";

            String superAdminPassword =
                    "SuperAdmin@123";


            // ====================================================
            // CHECK WHETHER SUPER ADMIN ALREADY EXISTS
            // ====================================================

            if (userRepository
                    .findByEmail(superAdminEmail)
                    .isEmpty()) {

                User superAdmin = new User();


                // =================================================
                // SUPER ADMIN DETAILS
                // =================================================

                superAdmin.setEmail(
                        superAdminEmail
                );

                superAdmin.setPassword(
                        passwordEncoder.encode(
                                superAdminPassword
                        )
                );

                superAdmin.setFirstName(
                        "Super"
                );

                superAdmin.setLastName(
                        "Admin"
                );

                superAdmin.setUsername(
                        "superadmin"
                );

                superAdmin.setRole(
                        Role.SUPER_ADMIN
                );

                superAdmin.setStatus(
                        "ACTIVE"
                );


                // =================================================
                // SAVE SUPER ADMIN
                // =================================================

                userRepository.save(
                        superAdmin
                );


                System.out.println(
                        "================================="
                );

                System.out.println(
                        "SUPERADMIN CREATED"
                );

                System.out.println(
                        "Email: "
                                + superAdminEmail
                );

                System.out.println(
                        "Password: "
                                + superAdminPassword
                );

                System.out.println(
                        "================================="
                );


            } else {

                System.out.println(
                        "SUPERADMIN already exists."
                );
            }
        };
    }
}