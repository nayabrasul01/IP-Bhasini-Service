package com.esic.checklist.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String ipNumber;
    private String password;
}
