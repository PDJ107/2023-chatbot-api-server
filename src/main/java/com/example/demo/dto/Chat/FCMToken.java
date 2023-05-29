package com.example.demo.dto.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FCMToken {
    private String fcmToken;

    public FCMToken() {
    }
}
