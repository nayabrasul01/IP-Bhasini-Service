package com.esic.checklist.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "checklist_questions")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "is_required")
    private Boolean isRequired;
    
    @Transient
    private String questionTextLocal;
    
    @Transient
    private String audioContent;
}
