package com.supercoding.Project1.service;


import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import com.supercoding.Project1.repository.PostRepository;
import com.supercoding.Project1.repository.UserRepository;
import com.supercoding.Project1.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Transactional
    public Post savePost(AuthInfo authInfo, Post post) {
        String userEmail = authInfo.getEmail();
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        if(user == null){
            logger.info("없는 이메일입니다.");
            return null;
        }else {
            logger.info("save Post");
            return postRepository.save(post);
        }
    }

    public List<PostResponse> allPost() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public Post updatePost(Long postId, PostRequest postRequest) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        existingPost.updatePost(postRequest.getTitle(),postRequest.getContent());
        return postRepository.save(existingPost);
    }

    public String deletePost(Long postId) {
        String message = "포스트 삭제에 실패했습니다.";
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        postRepository.delete(post);
        message = "포스트가 삭제되었습니다.";
        return message;
    }
}
