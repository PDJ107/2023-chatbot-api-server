package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.token.AccessTokenDTO;

public interface UserService {
    public AccessTokenDTO login(User user) throws Exception;
    public User getInfo(Long id) throws Exception;
    public User findMember(Long memberId);
}
