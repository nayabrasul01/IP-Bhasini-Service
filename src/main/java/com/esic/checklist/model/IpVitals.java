package com.esic.checklist.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "ip_vitals")
public class IpVitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_number", nullable = false, length = 50)
    private String ipNumber;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    @Column(name = "rbs", length = 20)
    private String rbs;

    @Column(name = "hemoglobin", length = 20)
    private String hemoglobin;

    @Column(name = "pulse", length = 20)
    private String pulse;

    @Column(name = "temperature", length = 20)
    private String temperature;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    
    @Transient
    private String status;
    
    @Transient
    private String message;
    
}
