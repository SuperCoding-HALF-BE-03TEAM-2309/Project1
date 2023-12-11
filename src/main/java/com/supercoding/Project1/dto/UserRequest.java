package com.supercoding.Project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {
    private String email;
    private String password;
    private String nickname;
    private Timestamp createdAt;
    private String token;
}
