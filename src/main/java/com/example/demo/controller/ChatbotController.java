package com.example.demo.controller;

import com.example.demo.dto.Chat.ChatResponse;
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
    @Operation(summary = "챗봇 질의응답", description = "챗봇과의 질의응답을 위한 api입니다.", tags = { "ChatbotController" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ChatResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/v1/chat")
    public ResponseEntity myInfo(@RequestBody String question, HttpServletRequest request) throws Exception {
        // 1. 내 정보 가져오기

        // 2. 질문 및 내 정보로 답변 요청 (python chatbot)

        // 3. 답변 반환
        ChatResponse response = new ChatResponse("테스트 답변입니다.");
        return ResponseEntity.ok().body(response);
    }
}
