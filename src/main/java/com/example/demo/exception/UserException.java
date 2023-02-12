package com.example.demo.exception;

public class UserException extends BaseException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
