package com.esic.checklist.service;

import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import com.esic.checklist.repository.QuestionRepository;
import com.esic.checklist.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    public List<Question> getQuestions(Long questionnaireId) {
        Questionnaire q = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found"));
        return questionRepository.findByQuestionnaire(q);
    }
}
