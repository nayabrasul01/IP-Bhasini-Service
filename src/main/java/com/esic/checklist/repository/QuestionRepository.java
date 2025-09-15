package com.esic.checklist.repository;

import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionnaire(Questionnaire questionnaire);
}
