package com.esic.checklist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}/questions/{targetLanguage}")
    public List<Question> getQuestions(@PathVariable Long id, @PathVariable String targetLanguage) {
    	List<Question> questions = questionnaireService.getQuestions(id);
    	questions.forEach( question -> {
    		TranslationResponse response = translationService.translate(question.getQuestionText(), "en", targetLanguage);
    		if(response != null && response.getPipelineResponse() != null && !response.getPipelineResponse().isEmpty() 
					&& response.getPipelineResponse().get(0).getOutput() != null 
					&& !response.getPipelineResponse().get(0).getOutput().isEmpty())
    			question.setQuestionTextLocal(response.getPipelineResponse().get(0).getOutput().get(0).getTarget());
    		else
    			question.setQuestionTextLocal("** अनुवाद उपलब्ध नहीं है **");
		});
        return questions;
    }
}
