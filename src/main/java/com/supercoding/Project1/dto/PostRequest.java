package com.supercoding.Project1.dto;


import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {
    private String title; // 제목
    private String content; // 내용
    private String email; // 이메일

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .email(email)
                .build();
    }

}
