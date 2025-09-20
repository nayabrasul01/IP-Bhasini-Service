package com.esic.checklist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esic.checklist.dto.ResponseDto;
import com.esic.checklist.model.Question;
import com.esic.checklist.model.Questionnaire;
import com.esic.checklist.model.Response;
import com.esic.checklist.repository.QuestionRepository;
import com.esic.checklist.repository.QuestionnaireRepository;
import com.esic.checklist.repository.ResponseRepository;

import lombok.RequiredArgsConstructor;

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
		response.setCreatedby(dto.getCreatedBy());

		responseRepository.save(response);
	}

	@Transactional
	public void saveOrUpdateResponse(ResponseDto response) {
		responseRepository.upsertResponse(response.getQuestionnaireId(), response.getQuestionId(), response.getUserId(),
				response.getResponseValue(), response.getCreatedBy());
	}

	public List<Response> getResponses(String userId, Long questionnaireId) {
		Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
				.orElseThrow(() -> new RuntimeException("Invalid questionnaire"));
		return responseRepository.findByUserIdAndQuestionnaire(userId, questionnaire);
	}
}
