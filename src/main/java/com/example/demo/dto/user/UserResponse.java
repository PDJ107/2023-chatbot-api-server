package com.example.demo.dto.user;

import com.example.demo.domain.User;
import com.example.demo.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@Getter
public class UserResponse {

    private String nickname;
    private String email;

    private Major major;
    private String studentId;
    private int grade;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getNickname(),
                user.getEmail(),
                user.getMajor(),
                user.getStudentId(),
                user.getGrade()
        );
    }
}
