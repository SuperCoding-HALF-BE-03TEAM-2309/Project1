package com.supercoding.Project1.service;

import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import com.supercoding.Project1.repository.PostRepository;
import com.supercoding.Project1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post writePost(String title, String body) {
        UserEntity writer = userRepository.findByEmail().orElseThrow();
        return postRepository.save(
                Post.builder()
                        .title(title)
                        .content(body)
                        .created_at(LocalDateTime.now())
                        .build()
        );
    }

    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> PostResponse).collect(Collectors.toList());
    }

    public Post updatePost(Integer postId, PostRequest postRequest) {

    }

    public void deletePostById(Integer postId) {
    }
}
