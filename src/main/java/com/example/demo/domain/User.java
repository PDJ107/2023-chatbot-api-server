package com.example.demo.domain;

import com.example.demo.enums.Major;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter @SuperBuilder
public class User extends CustomEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String nickname;
    private String password;
    private String email;
    private Boolean isAnswering;

    @Enumerated(EnumType.STRING)
    private Major major;
    private String studentId;
    private int grade;

    protected User() {}

    public void setAnswering(Boolean answering) {
        isAnswering = answering;
    }
}