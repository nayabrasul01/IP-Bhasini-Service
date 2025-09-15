package com.esic.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

@Service
public class TranslationService {

    @Autowired
    private Translate translateClient;

    /**
     * Translate given text into target language.
     * If targetLanguage is null or empty, use "hi" (Hindi) by default.
     */
    public String translateText(String text, String targetLanguage) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String tgtLang = (targetLanguage == null || targetLanguage.isEmpty()) ? "hi" : targetLanguage;

        Translation translation = translateClient.translate(
            text,
            Translate.TranslateOption.targetLanguage(tgtLang),
            Translate.TranslateOption.model("base")  // can use "nmt" or other if available
        );

        return translation.getTranslatedText();
    }
}
