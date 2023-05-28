package com.example.demo.dto.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatusRequest {
    long user_id;
    Boolean isAnswering;
}
