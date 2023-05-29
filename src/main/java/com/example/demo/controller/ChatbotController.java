package com.example.demo.controller;

import com.example.demo.dto.Chat.ChatRequest;
import com.example.demo.dto.Chat.FCMToken;
import com.example.demo.dto.Chat.StatusRequest;
import com.example.demo.intercepter.CurrentUserInfo;
import com.example.demo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatbotController {
    private final ChatService chatService;

    @Operation(summary = "챗봇 질의응답", description = "챗봇과의 질의응답을 위한 api입니다. JWT 토큰과 FCM 토큰이 필요합니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/v1/chat")
    public ResponseEntity chat(@RequestBody ChatRequest chatRequest, HttpServletRequest request) throws Exception {
        // 1. 내 정보 가져오기
        CurrentUserInfo user = (CurrentUserInfo) request.getAttribute("CurrentUserInfo");
        System.out.println(chatRequest.getQuestion());
        System.out.println(chatRequest.getFcmToken());
        // 2. 질문 및 내 정보로 답변 요청 (python chatbot)
        chatService.request(user.getId(), chatRequest);

        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "답변 상태 변경", description = "답변중인지 여부를 업데이트합니다. JWT 토큰과 FCM 토큰이 필요합니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/v1/status")
    public ResponseEntity updateChatStatus(@RequestBody StatusRequest statusRequest) throws Exception {
        chatService.updateStatus(statusRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "context 초기화", description = "현재 검색된 context를 초기화합니다.(주제변경) JWT 토큰과 FCM 토큰이 필요합니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/v1/context")
    public ResponseEntity contextSwitching(@RequestBody FCMToken fcmToken, HttpServletRequest request) throws Exception {
        CurrentUserInfo user = (CurrentUserInfo) request.getAttribute("CurrentUserInfo");
        chatService.initContext(user.getId(), fcmToken.getFcmToken());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "출처 요청", description = "현재 검색된 context의 출처를 요청합니다. JWT 토큰과 FCM 토큰이 필요합니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/v1/source")
    public ResponseEntity getSource(@RequestBody FCMToken fcmToken, HttpServletRequest request) throws Exception {
        CurrentUserInfo user = (CurrentUserInfo) request.getAttribute("CurrentUserInfo");
        chatService.getSource(user.getId(), fcmToken.getFcmToken());
        return ResponseEntity.accepted().build();
    }
}
