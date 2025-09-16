package com.esic.checklist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esic.checklist.model.TranslationResponse;
import com.esic.checklist.service.TranslationService;

@RestController
@RequestMapping("/translate")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<TranslationResponse> translate(
            @RequestParam String text,
            @RequestParam(required = false, defaultValue = "en") String sourceLang,
            @RequestParam(required = false, defaultValue = "hi") String targetLang) {

        TranslationResponse response = translationService.translate(text, sourceLang, targetLang);
        return ResponseEntity.ok(response);
    }
}
