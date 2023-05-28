package com.example.demo.exceptions;

public class ChatException extends BaseException {
    public ChatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
