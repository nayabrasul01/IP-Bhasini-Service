package com.esic.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esic.checklist.dto.TranslateRequest;
import com.esic.checklist.dto.TranslateResponse;
import com.esic.checklist.service.TranslationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bhasini")
@RequiredArgsConstructor
public class BhasiniController {

	@Autowired
	private TranslationService translationService;

//    private final BhasiniService bhasiniService;
//
//    @PostMapping("/translate")
//    public String translate(@RequestParam String text,
//                            @RequestParam String sourceLang,
//                            @RequestParam String targetLang) {
//        return bhasiniService.translate(text, sourceLang, targetLang);
//    }
//
//    @PostMapping("/tts")
//    public ResponseEntity<byte[]> tts(@RequestParam String text, @RequestParam String lang) {
//        byte[] audio = bhasiniService.textToSpeech(text, lang);
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(audio);
//    }
//
//    @PostMapping("/asr")
//    public String asr(@RequestBody byte[] audio, @RequestParam String lang) {
//        return bhasiniService.speechToText(audio, lang);
//    }

	@PostMapping
	public TranslateResponse translate(@RequestBody TranslateRequest request) {
		String translated = translationService.translateText(request.getText(), request.getTargetLanguage());
		return new TranslateResponse(translated);
	}
}
