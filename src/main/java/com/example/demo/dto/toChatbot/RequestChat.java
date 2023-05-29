package com.example.demo.dto.toChatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestChat {
    String question;
    String user_id;
    String fcm_token;
}
