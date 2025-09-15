package com.esic.checklist.dto;

public class TranslateResponse {
	private String translatedText;

	public TranslateResponse(String translatedText) {
		this.translatedText = translatedText;
	}

	public String getTranslatedText() {
		return translatedText;
	}

	public void setTranslatedText(String translatedText) {
		this.translatedText = translatedText;
	}
}
