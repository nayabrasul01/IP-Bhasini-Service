package com.esic.checklist.service;

import com.esic.checklist.dto.ResponseDto;
import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import com.esic.checklist.model.Response;
import com.esic.checklist.repository.QuestionRepository;
import com.esic.checklist.repository.QuestionnaireRepository;
import com.esic.checklist.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    public void saveResponse(ResponseDto dto) {
        Questionnaire questionnaire = questionnaireRepository.findById(dto.getQuestionnaireId())
                .orElseThrow(() -> new RuntimeException("Invalid questionnaire"));
        Question question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Invalid question"));

        Response response = new Response();
        response.setQuestionnaire(questionnaire);
        response.setQuestion(question);
        response.setUserId(dto.getUserId());
        response.setResponseValue(dto.getResponseValue());

        responseRepository.save(response);
    }

    public List<Response> getResponses(Long userId, Long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new RuntimeException("Invalid questionnaire"));
        return responseRepository.findByUserIdAndQuestionnaire(userId, questionnaire);
    }
}
