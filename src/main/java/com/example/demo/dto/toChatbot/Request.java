package com.example.demo.dto.toChatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request {
    String user_id;
    String fcm_token;
}
