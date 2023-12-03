package com.supercoding.Project1.controller;


import com.supercoding.Project1.dto.UserRequest;
import com.supercoding.Project1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signup(@RequestBody UserRequest userRequest) {
        boolean isSuccess = userService.signUp(userRequest);
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "이미 등록된 이메일입니다."));
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest userRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = userService.login(userRequest);
        Map<String, String> response = new HashMap<>();

        if (token != null) {
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            response.put("message", "로그인 성공");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody UserRequest userRequest) {
        boolean isLoggedOut = userService.logout(userRequest.getToken());

        if (isLoggedOut) {
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.badRequest().body("로그아웃 실패");
        }
    }
}
