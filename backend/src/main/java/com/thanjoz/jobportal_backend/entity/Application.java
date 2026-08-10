
package com.thanjoz.jobportal_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "applications")
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long jobId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;
}

