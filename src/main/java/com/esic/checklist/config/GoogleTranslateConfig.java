package com.esic.checklist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

@Configuration
public class GoogleTranslateConfig {

    @Value("${google.project-id}")
    private String projectId;

    @Value("${google.translate.location:global}")
    private String location;  // e.g. "global" or region

    @Value("${google.api-key}")
    private String credentialsPath;

    @Bean
    public Translate googleTranslate() {
        // Set credentials via environment variable or explicitly
        TranslateOptions options = TranslateOptions.newBuilder()
                .setProjectId(projectId)
                .build();
        return options.getService();
    }
}

