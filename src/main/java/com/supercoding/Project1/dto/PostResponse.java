package com.supercoding.Project1.dto;

import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class PostResponse {
    private Integer post_id;
    private String title;
    private String content;
    private UserEntity user;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public static PostResponse from(Post post){
        return PostResponse.builder()
                .post_id(post.getPost_id())
                .title(post.getTitle())
                .content(post.getContent())
                .user(post.getUser())
                .created_at(post.getCreated_at()).build();
    }
}
