package com.esic.checklist.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.esic.checklist.model.TranslationRequest;
import com.esic.checklist.model.TranslationResponse;
import com.esic.checklist.utility.TranslationRequestBuilder;

@Service
public class TranslationService {

	@Value("${bhashini.api.endpoint}")
	private String apiUrl;

	@Value("${bhashini.api.ulcaApiKey}")
	private String ulcaApiKey;

	@Value("${bhashini.api.userId}")
	private String userId;

	@Value("${bhashini.translation.serviceId}")
	private String translationServiceId;

	@Value("${bhashini.tts.serviceId}")
	private String ttsServiceId;

	private RestTemplate restTemplate = new RestTemplate();

	public TranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

	public TranslationResponse translate(String text, String sourceLang, String targetLang) {
		// Build request
		TranslationRequest request = TranslationRequestBuilder.buildTranslationRequest(text, sourceLang, targetLang,
				translationServiceId);

		try {
			// Prepare headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("ulcaApiKey", ulcaApiKey);
			headers.set("userID", userId);

			HttpEntity<TranslationRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<TranslationResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity,
					TranslationResponse.class);

			return response.getBody();
		} catch (ResourceAccessException ex) {
			// This happens if timeout occurs
			return fallbackResponse(request, "Timeout occurred, falling back to Hindi.");
		} catch (Exception ex) {
			// Any other error
			return fallbackResponse(request, "Error occurred, falling back to Hindi.");
		}
	}

	private TranslationResponse fallbackResponse(TranslationRequest request, String message) {
		TranslationResponse fallback = new TranslationResponse();

		TranslationResponse.PipelineResponse pipelineResponse = new TranslationResponse.PipelineResponse();
		pipelineResponse.setTaskType("translation");

		TranslationResponse.Output output = new TranslationResponse.Output();
		output.setSource(request.getInputData().getInput().get(0).getSource());
		output.setTarget("अनुवाद उपलब्ध नहीं है");

		pipelineResponse.setOutput(Collections.singletonList(output));

		fallback.setPipelineResponse(Collections.singletonList(pipelineResponse));
		return fallback;
	}
}
