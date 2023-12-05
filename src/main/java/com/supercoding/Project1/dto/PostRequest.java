package com.supercoding.Project1.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostRequest {
    private String title;
    private String content;
    private UserEntity user;

    @Builder
    public PostRequest(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                // Todo .user(user.getEmail()) // 유저 이메일 가져오기
                .build();
    }

}
