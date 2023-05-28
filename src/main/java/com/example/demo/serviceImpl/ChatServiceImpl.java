package com.example.demo.serviceImpl;

import com.example.demo.domain.User;
import com.example.demo.dto.Chat.ChatRequest;
import com.example.demo.dto.Chat.StatusRequest;
import com.example.demo.exceptions.ChatException;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {
    private UserService userService;

    @Override
    public void request(long user_id, ChatRequest chatRequest) throws Exception {
        User user = userService.getInfo(user_id);

        // 현재 답변중인경우
        if(user.getIsAnswering()) {
            throw new ChatException(ErrorCode.Answering_User);
        }
        userService.updateAnsweringStatus(user_id, true);

        // 요청

    }

    @Override
    public void updateStatus(StatusRequest statusRequest) throws Exception {
        userService.updateAnsweringStatus(statusRequest.getUser_id(), statusRequest.getIsAnswering());
    }

    @Override
    public void contextSwitching(long user_id, String fcmToken) throws Exception {
        User user = userService.getInfo(user_id);

        // 현재 답변중인경우
        if(user.getIsAnswering()) {
            throw new ChatException(ErrorCode.Answering_User);
        }
        userService.updateAnsweringStatus(user_id, true);

        // 요청

    }

    @Override
    public void getSource(long user_id, String fcmToken) throws Exception {
        User user = userService.getInfo(user_id);

        // 현재 답변중인경우
        if(user.getIsAnswering()) {
            throw new ChatException(ErrorCode.Answering_User);
        }
        userService.updateAnsweringStatus(user_id, true);

        // 요청

    }
}
