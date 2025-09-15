package com.esic.checklist.repository;

import com.esic.checklist.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}
