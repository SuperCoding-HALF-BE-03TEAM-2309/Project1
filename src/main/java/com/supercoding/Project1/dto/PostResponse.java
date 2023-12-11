package com.supercoding.Project1.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponse {
    private Long postId; // PK
    private String title; // 제목
    private String content; // 내용
    private String email; // 유저 이메일
    private Timestamp createdAt, updatedAt; // 생성일, 업데이트일

    public static PostResponse from(Post post){
        PostResponse.PostResponseBuilder postResponseBuilder = PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreateAt())
                .updatedAt(post.getUpdateAt());

        if (post.getEmail() != null){
            postResponseBuilder.email(post.getEmail());
        }
        return postResponseBuilder.build();
    }
    public PostResponse(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.email = post.getEmail();
        this.createdAt = post.getCreateAt();
        this.updatedAt = post.getUpdateAt();
    }

}
