package com.example.demo.dto.user;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
public class LoginDTO {
    @NotNull(message = "email 값이 Null 입니다.")
    public final String email;

    @NotNull(message = "password 값이 Null 입니다.")
    public final String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}