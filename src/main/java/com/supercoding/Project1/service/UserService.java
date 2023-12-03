package com.supercoding.Project1.service;

import com.supercoding.Project1.dto.UserRequest;
import com.supercoding.Project1.entity.UserEntity;
import com.supercoding.Project1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public boolean signUp(UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        // 이미 등록된 이메일인지 확인
        if(userRepository.existsByEmail(email)){
            return false; // 이미 등록된 이메일이라면 가입 실패
        };

        // 패스워드 암호화
        String encryptedPassword = passwordEncoder.encode(password);

        // 회원 생성 및 저장
        UserEntity newUser = UserEntity.builder()
                .email(email)
                .password(encryptedPassword)
                .build();

        userRepository.save(newUser);
        return true; // 가입 성공
    }

    public String login(UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(password, userEntity.getPassword())){
            // 비밀번호가 일치하는 경우, JWT 토큰 생성
            String token = jwtService.encode(userEntity.getId());
            return token;
        } else {
            throw new RuntimeException("Invalid password");
        }


    }

    public boolean logout(String token) {
        return true;
    }
}
