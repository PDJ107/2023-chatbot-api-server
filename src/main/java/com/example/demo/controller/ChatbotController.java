package com.example.demo.controller;

import com.example.demo.dto.Chat.ChatRequest;
import com.example.demo.intercepter.CurrentUserInfo;
import com.example.demo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private ChatService chatService;

    @Operation(summary = "챗봇 질의응답", description = "챗봇과의 질의응답을 위한 api입니다.", tags = { "ChatbotController" })
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

        // 2. 질문 및 내 정보로 답변 요청 (python chatbot)
        chatService.request(user.getId(), chatRequest);

        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "답변 상태 변경", description = "답변중인지 여부를 업데이트합니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/v1/status")
    public ResponseEntity updateChatStatus(@RequestBody Long user_id, @RequestBody Boolean isAnswering) throws Exception {
        chatService.updateStatus(user_id, isAnswering);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/context")
    public ResponseEntity contextSwitching(@RequestBody String fcmToken, HttpServletRequest request) throws Exception {
        CurrentUserInfo user = (CurrentUserInfo) request.getAttribute("CurrentUserInfo");
        chatService.contextSwitching(user.getId(), fcmToken);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/v1/source")
    public ResponseEntity getSource(@RequestBody String fcmToken, HttpServletRequest request) throws Exception {
        CurrentUserInfo user = (CurrentUserInfo) request.getAttribute("CurrentUserInfo");
        chatService.getSource(user.getId(), fcmToken);
        return ResponseEntity.accepted().build();
    }
}
