package com.esic.checklist.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "logged_in_users")
public class LoggedInUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId; // store JWT jti

    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "is_valid")
    private boolean isValid;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
