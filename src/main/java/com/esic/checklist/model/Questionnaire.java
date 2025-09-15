package com.esic.checklist.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checklist_questionnaires")
@Data
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionnaire_id")
    private Long questionnaireId;

    private String name;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
