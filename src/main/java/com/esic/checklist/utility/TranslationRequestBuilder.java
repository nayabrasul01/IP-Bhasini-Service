package com.esic.checklist.utility;

import java.util.Arrays;
import java.util.Collections;

import com.esic.checklist.model.TranslationRequest;

public class TranslationRequestBuilder {

    public static TranslationRequest buildTranslationRequest(String sourceText, String sourceLang, String targetLang, String translationServiceId, String ttsServiceId) {
        TranslationRequest request = new TranslationRequest();

        // Input
        TranslationRequest.Input input = new TranslationRequest.Input();
        input.setSource(sourceText);

        TranslationRequest.InputData inputData = new TranslationRequest.InputData();
        inputData.setInput(Collections.singletonList(input));
        inputData.setAudio(Collections.emptyList());

        // Config for translation task
        TranslationRequest.Language language = new TranslationRequest.Language();
        language.setSourceLanguage(sourceLang != null ? sourceLang : "en");
        language.setTargetLanguage(targetLang != null ? targetLang : "hi");

        TranslationRequest.Config config = new TranslationRequest.Config();
        config.setLanguage(language);
        config.setServiceId(translationServiceId != null ? translationServiceId : "ai4bharat/indictrans-v2-all-gpu--t4");

        TranslationRequest.PipelineTask pipelineTask = new TranslationRequest.PipelineTask();
        pipelineTask.setTaskType("translation");
        pipelineTask.setConfig(config);
        
//        // Config for TTS
        TranslationRequest.Language ttsLanguage = new TranslationRequest.Language();
        ttsLanguage.setSourceLanguage(targetLang != null ? targetLang : "hi");

        TranslationRequest.Config ttsConfig = new TranslationRequest.Config();
        ttsConfig.setLanguage(ttsLanguage);
        ttsConfig.setServiceId(ttsServiceId != null ? ttsServiceId : "ai4bharat/indic-tts-coqui-dravidian-gpu--t4");
        ttsConfig.setGender("female");

        TranslationRequest.PipelineTask ttsPipelineTask = new TranslationRequest.PipelineTask();
        ttsPipelineTask.setTaskType("tts");
        ttsPipelineTask.setConfig(ttsConfig);

        // Set to root object
        request.setPipelineTasks(Arrays.asList(pipelineTask, ttsPipelineTask));
//        request.setPipelineTasks(Collections.singletonList(pipelineTask));
        request.setInputData(inputData);

        return request;
    }
}
