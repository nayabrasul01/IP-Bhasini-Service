package com.esic.checklist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.esic.checklist.annotations.Traceable;
import com.esic.checklist.model.IpVitals;
import com.esic.checklist.model.Question;
import com.esic.checklist.model.TranslationResponse;
import com.esic.checklist.service.QuestionnaireService;
import com.esic.checklist.service.TranslationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questionnaires")
@RequiredArgsConstructor
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;
	private final TranslationService translationService;
	private final RestTemplate restTemplate;

	@Value("${url.his.endpoint}")
	private String hisEndpoint;

	@Traceable
	@PostMapping("/vitals")
	public ResponseEntity<IpVitals> saveVitals(@RequestBody IpVitals vitals) {
		if (vitals == null || vitals.getIpNumber() == null || vitals.getIpNumber().isEmpty()) {
			vitals.setStatus("false");
			vitals.setMessage("Patient IP number is required");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vitals);
		}
		questionnaireService.saveVitals(vitals);
		vitals.setStatus("true");
		vitals.setMessage("Vitals saved successfully");
		return ResponseEntity.status(HttpStatus.OK).body(vitals);
	}

	@Traceable
	@GetMapping("/vitals/{ipNumber}")
	public ResponseEntity<List<IpVitals>> getVitals(@PathVariable String ipNumber) {
		List<IpVitals> vitalsList = questionnaireService.getVitals(ipNumber);
		if (vitalsList == null || vitalsList.isEmpty()) {
			IpVitals vitals = new IpVitals();
			vitals.setIpNumber(ipNumber);
			vitals.setMessage("No vitals found for the given IP Number");
			vitals.setStatus("false");
			vitalsList = new ArrayList<IpVitals>();
			vitalsList.add(vitals);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vitalsList);
		}
//		vitals.setMessage("Vitals fetched successfully");
//		vitals.setStatus("true");
		return ResponseEntity.status(HttpStatus.OK).body(vitalsList);
	}

	@GetMapping("/{id}/questions/{targetLanguage}")
	public List<Question> getQuestions(@PathVariable Long id, @PathVariable String targetLanguage) {
		List<Question> questions = questionnaireService.getQuestions(id);
		// for testing only
//    	for(int i = 0; i < questions.size() - 2; i++) {
//			questions.remove(i);
//		}
		questions.forEach(question -> {
			TranslationResponse response = translationService.translate(question.getQuestionText(), "en",
					targetLanguage);
			// Set translated text if available
			if (response != null && response.getPipelineResponse() != null && !response.getPipelineResponse().isEmpty()
					&& response.getPipelineResponse().get(0).getOutput() != null
					&& !response.getPipelineResponse().get(0).getOutput().isEmpty()) {
				question.setQuestionTextLocal(response.getPipelineResponse().get(0).getOutput().get(0).getTarget());

				// Set audio content if available
				if (response.getPipelineResponse().size() > 1
						&& response.getPipelineResponse().get(1).getAudio() != null
						&& !response.getPipelineResponse().get(1).getAudio().isEmpty()
						&& response.getPipelineResponse().get(1).getAudio().get(0) != null) {
					question.setAudioContent(response.getPipelineResponse().get(1).getAudio().get(0).getAudioContent());
				}
			} else
				question.setQuestionTextLocal("** अनुवाद उपलब्ध नहीं है **");
		});
		return questions;
	}

	@Traceable
	@PostMapping("/getIpDetails")
	public ResponseEntity<Object> fetchIpDetails(@RequestBody Map<String, Object> request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
		Map<String, String> responseBody = new HashMap<String, String>();
		try {
			ResponseEntity<Object> response = restTemplate.postForEntity(hisEndpoint, entity, Object.class);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception ex) {
			responseBody.put("status", "failure");
			responseBody.put("message", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
		}
	}
}
