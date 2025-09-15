package com.esic.checklist.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checklist_responses")
@Data
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "response_value")
    private String responseValue; // Y / N

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
