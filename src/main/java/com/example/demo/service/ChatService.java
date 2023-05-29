package com.example.demo.service;

import com.example.demo.dto.Chat.ChatRequest;
import com.example.demo.dto.Chat.StatusRequest;

public interface ChatService {
    void request(long user_id, ChatRequest chatRequest) throws Exception;
    void updateStatus(StatusRequest statusRequest) throws Exception;
    void initContext(long user_id, String fcmToken) throws Exception;
    void getSource(long user_id, String fcmToken) throws Exception;
}
