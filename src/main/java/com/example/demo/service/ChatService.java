package com.example.demo.service;

import com.example.demo.dto.Chat.ChatRequest;

public interface ChatService {
    void request(long user_id, ChatRequest chatRequest) throws Exception;
    void updateStatus(long user_id, Boolean isAnswering) throws Exception;
    void contextSwitching(long user_id, String fcmToken) throws Exception;
    void getSource(long user_id, String fcmToken) throws Exception;
}
