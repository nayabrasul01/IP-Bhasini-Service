package com.esic.checklist.model;

import java.util.List;

public class TranslationRequest {

    private List<PipelineTask> pipelineTasks;
    private InputData inputData;

    // Getters & Setters
    public List<PipelineTask> getPipelineTasks() {
        return pipelineTasks;
    }
    public void setPipelineTasks(List<PipelineTask> pipelineTasks) {
        this.pipelineTasks = pipelineTasks;
    }

    public InputData getInputData() {
        return inputData;
    }
    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

    // Nested Classes

    public static class PipelineTask {
        private String taskType;
        private Config config;

        public String getTaskType() {
            return taskType;
        }
        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public Config getConfig() {
            return config;
        }
        public void setConfig(Config config) {
            this.config = config;
        }
    }

    public static class Config {
        private Language language;
        private String serviceId;
        private String gender;

		public Language getLanguage() {
            return language;
        }
        public void setLanguage(Language language) {
            this.language = language;
        }

        public String getServiceId() {
            return serviceId;
        }
        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
        public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
    }

    public static class Language {
        private String sourceLanguage;
        private String targetLanguage;

        public String getSourceLanguage() {
            return sourceLanguage;
        }
        public void setSourceLanguage(String sourceLanguage) {
            this.sourceLanguage = sourceLanguage;
        }

        public String getTargetLanguage() {
            return targetLanguage;
        }
        public void setTargetLanguage(String targetLanguage) {
            this.targetLanguage = targetLanguage;
        }
    }

    public static class InputData {
        private List<Input> input;
        private List<String> audio;

        public List<Input> getInput() {
            return input;
        }
        public void setInput(List<Input> input) {
            this.input = input;
        }

        public List<String> getAudio() {
            return audio;
        }
        public void setAudio(List<String> audio) {
            this.audio = audio;
        }
    }

    public static class Input {
        private String source;

        public String getSource() {
            return source;
        }
        public void setSource(String source) {
            this.source = source;
        }
    }
}

