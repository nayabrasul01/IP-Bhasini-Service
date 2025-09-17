package com.esic.checklist.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private Long questionnaireId;
    private Long questionId;
    private String userId;
    private String responseValue;
}
