package com.example.demo.serviceImpl;

import com.example.demo.domain.User;
import com.example.demo.dto.Chat.ChatRequest;
import com.example.demo.dto.Chat.StatusRequest;
import com.example.demo.dto.toChatbot.Request;
import com.example.demo.dto.toChatbot.RequestChat;
import com.example.demo.exceptions.ChatException;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import com.example.demo.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {
    @Value("${chat.server-url}")
    private String chatUrl;
    private final UserService userService;
    private final HttpUtil httpUtil;
    @Autowired
    private final ObjectMapper objectMapper;

    private HttpClient getHttpClient() {
        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }


    @Override
    public void request(long user_id, ChatRequest chatRequest) throws Exception {
        User user = userService.getInfo(user_id);

        // 현재 답변중인경우
        if(user.getIsAnswering()) {
            throw new ChatException(ErrorCode.Answering_User);
        }
        userService.updateAnsweringStatus(user_id, true);

        // 요청
        URL url = new URL(new URL(chatUrl), "chatbot");
        RequestChat request = new RequestChat(
                chatRequest.getQuestion(),
                String.valueOf(user_id),
                chatRequest.getFcmToken()
        );
        System.out.println(url.toString());
        try {
            httpUtil.post(getHttpClient(), url.toString(), objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            userService.updateAnsweringStatus(user_id, false);
            throw new ChatException(ErrorCode.Chat_Request_Failed);
        }
    }

    @Override
    public void updateStatus(StatusRequest statusRequest) throws Exception {
        userService.updateAnsweringStatus(statusRequest.getUser_id(), statusRequest.getIsAnswering());
    }

    @Override
    public void initContext(long user_id, String fcmToken) throws Exception {
        User user = userService.getInfo(user_id);

        // 현재 답변중인경우
        if(user.getIsAnswering()) {
            throw new ChatException(ErrorCode.Answering_User);
        }
        userService.updateAnsweringStatus(user_id, true);

        // 요청
        URL url = new URL(new URL(chatUrl), "chatbot/init");
        Request request = new Request(
                String.valueOf(user_id),
                fcmToken
        );
        try {
            httpUtil.post(getHttpClient(), url.toString(), objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            userService.updateAnsweringStatus(user_id, true);
            throw new ChatException(ErrorCode.Chat_Request_Failed);
        }
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
        URL url = new URL(new URL(chatUrl), "chatbot/source");
        Request request = new Request(
                String.valueOf(user_id),
                fcmToken
        );
        try {
            httpUtil.post(getHttpClient(), url.toString(), objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            userService.updateAnsweringStatus(user_id, true);
            throw new ChatException(ErrorCode.Chat_Request_Failed);
        }
    }
}
