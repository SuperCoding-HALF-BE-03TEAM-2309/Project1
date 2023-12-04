package com.supercoding.Project1.dto;

import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostResponse {
    private long post_id;
    private String title;
    private String content;
    private String email;

    public static PostResponse from(Post post){
        PostResponse postResponse = PostResponse.builder()
                .post_id(post.getPost_id())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        if (post.getUser() != null){
            postResponse.setEmail(post.getUser().getEmail());
        }
        return postResponse;
    }
}
