package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.token.AccessTokenDTO;

public interface UserService {
    AccessTokenDTO login(User user) throws Exception;
    User getInfo(Long id) throws Exception;
    User findMember(Long memberId);
    void updateAnsweringStatus(Long user_id, Boolean isAnswering) throws Exception;
}
