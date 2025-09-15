package com.esic.checklist.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BhasiniService {

//    private final RestTemplate restTemplate = new RestTemplate();
//    private final String BASE_URL = "https://bhashini.gov.in/api"; // Example
//
//    public String translate(String text, String sourceLang, String targetLang) {
//        String url = BASE_URL + "/translate";
//        Map<String, String> req = Map.of("text", text, "sourceLang", sourceLang, "targetLang", targetLang);
//        return restTemplate.postForObject(url, req, String.class);
//    }
//
//    public byte[] textToSpeech(String text, String lang) {
//        String url = BASE_URL + "/tts";
//        Map<String, String> req = Map.of("text", text, "lang", lang);
//        return restTemplate.postForObject(url, req, byte[].class);
//    }
//
//    public String speechToText(byte[] audio, String lang) {
//        String url = BASE_URL + "/asr?lang=" + lang;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        HttpEntity<byte[]> entity = new HttpEntity<>(audio, headers);
//        return restTemplate.postForObject(url, entity, String.class);
//    }
}
