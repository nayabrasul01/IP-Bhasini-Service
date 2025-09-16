package com.esic.checklist.model;

import java.util.List;

public class TranslationResponse {

    private List<PipelineResponse> pipelineResponse;

    public List<PipelineResponse> getPipelineResponse() {
        return pipelineResponse;
    }
    public void setPipelineResponse(List<PipelineResponse> pipelineResponse) {
        this.pipelineResponse = pipelineResponse;
    }

    // Nested Classes

    public static class PipelineResponse {
        private String taskType;
        private Object config;  // Since "config": null in response
        private List<Output> output;
        private List<Audio> audio;

        public String getTaskType() {
            return taskType;
        }
        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public Object getConfig() {
            return config;
        }
        public void setConfig(Object config) {
            this.config = config;
        }

        public List<Output> getOutput() {
            return output;
        }
        public void setOutput(List<Output> output) {
            this.output = output;
        }

        public List<Audio> getAudio() {
            return audio;
        }
        public void setAudio(List<Audio> audio) {
            this.audio = audio;
        }
    }

    public static class Output {
        private String source;
        private String target;

        public String getSource() {
            return source;
        }
        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }
        public void setTarget(String target) {
            this.target = target;
        }
    }

    public static class Audio {
        private String audioContent;
        private String audioUri;

        public String getAudioContent() {
            return audioContent;
        }
        public void setAudioContent(String audioContent) {
            this.audioContent = audioContent;
        }

        public String getAudioUri() {
            return audioUri;
        }
        public void setAudioUri(String audioUri) {
            this.audioUri = audioUri;
        }
    }
}

