package com.supercoding.Project1.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.supercoding.Project1.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponse {
    private long postId;
    private String title;
    private String content;
    private String email; // 유저 이메일
    private LocalDateTime createdAt, updatedAt;

//    public static PostResponse from(Post post){
//        PostResponse.PostResponseBuilder postResponseBuilder = PostResponse.builder()
//                .postId(post.getPostId())
//                .title(post.getTitle())
//                .content(post.getContent())
//                .createdAt(post.getCreatedAt())
//                .updatedAt(post.getUpdatedAt());
//
//        if (post.getUser() != null){
//            postResponseBuilder.email(post.getUser().getEmail());
//        }
//        return postResponseBuilder.build();
//    }
    public PostResponse(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.email = post.getUser().getEmail();
        this.createdAt = post.getCreatedAt();
    }
}
