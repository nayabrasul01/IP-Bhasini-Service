package com.esic.checklist.controller;

import com.esic.checklist.model.Question;
import com.esic.checklist.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionnaires")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @GetMapping("/{id}/questions")
    public List<Question> getQuestions(@PathVariable Long id) {
        return questionnaireService.getQuestions(id);
    }
}
