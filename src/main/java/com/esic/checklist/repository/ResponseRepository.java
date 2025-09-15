package com.esic.checklist.repository;

import com.esic.checklist.model.Response;
import com.esic.checklist.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByUserIdAndQuestionnaire(Long userId, Questionnaire questionnaire);
}
