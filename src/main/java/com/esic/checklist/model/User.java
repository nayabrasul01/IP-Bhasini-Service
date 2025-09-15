package com.esic.checklist.model;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_number", nullable = false, unique = true)
    private String ipNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String password;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
