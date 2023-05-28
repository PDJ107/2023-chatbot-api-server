package com.example.demo.dto.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatRequest {
    private String question;
    private String fcmToken;
}
