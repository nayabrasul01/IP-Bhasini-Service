package com.esic.checklist.controller;

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

import com.esic.checklist.model.Question;
import com.esic.checklist.model.TranslationResponse;
import com.esic.checklist.service.QuestionnaireService;
import com.esic.checklist.service.TranslationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/questionnaires")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    
    private final TranslationService translationService;
    
    private RestTemplate restTemplate = new RestTemplate();
    
    @Value("${url.his.endpoint}")
	private String hisEndpoint;

    @GetMapping("/{id}/questions/{targetLanguage}")
    public List<Question> getQuestions(@PathVariable Long id, @PathVariable String targetLanguage) {
    	List<Question> questions = questionnaireService.getQuestions(id);
//    	for(int i = 0; i < questions.size() - 5; i++) {
//			questions.remove(i);
//		}
    	questions.forEach( question -> {
    		TranslationResponse response = translationService.translate(question.getQuestionText(), "en", targetLanguage);
    		// Set translated text if available
    		if(response != null && response.getPipelineResponse() != null && !response.getPipelineResponse().isEmpty() 
					&& response.getPipelineResponse().get(0).getOutput() != null 
					&& !response.getPipelineResponse().get(0).getOutput().isEmpty()) {
    			question.setQuestionTextLocal(response.getPipelineResponse().get(0).getOutput().get(0).getTarget());
    			
    			// Set audio content if available
    			if(response.getPipelineResponse().size() > 1 && response.getPipelineResponse().get(1).getAudio() != null 
						&& !response.getPipelineResponse().get(1).getAudio().isEmpty()
						&& response.getPipelineResponse().get(1).getAudio().get(0) != null) {
    				question.setAudioContent(response.getPipelineResponse().get(1).getAudio().get(0).getAudioContent());
				}
    		}
    		else
    			question.setQuestionTextLocal("** अनुवाद उपलब्ध नहीं है **");
		});
        return questions;
    }
    
    @PostMapping("/getIpDetails")
    public ResponseEntity<Object> fetchIpDetails(@RequestBody Map<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(hisEndpoint, entity, Object.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                 .body(ex.getMessage());
        }
    }
}
