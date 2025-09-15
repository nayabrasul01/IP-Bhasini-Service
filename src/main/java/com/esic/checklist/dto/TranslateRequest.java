package com.esic.checklist.dto;

public class TranslateRequest {
	private String text;
	private String targetLanguage; // e.g. "hi", "te", "ta", "bn", etc.

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}
}
