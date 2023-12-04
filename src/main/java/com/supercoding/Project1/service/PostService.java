package com.supercoding.Project1.service;

import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import com.supercoding.Project1.repository.PostRepository;
import com.supercoding.Project1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post writePost(String title, String content, Long memberId) {
        Post post = new Post(userRepository.findById(memberId).orElseThrow(), title, content);
        return postRepository.save(post);

    }

    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(PostResponse::from).collect(Collectors.toList());
    }

    public Post updatePost(Integer postId, PostRequest postRequest) {
        Optional<Post> result = postRepository.findById(postId);

        if(result.isPresent()){
            Post entity = result.get();

            entity.setTitle(postRequest.getTitle());
            entity.setContent(postRequest.getContent());

            postRepository.save(entity);
        }
    }

    public boolean deletePostById(long id, @PathVariable Integer postId) {
        Post post = postRepository.getReferenceById(postId);
        UserEntity user = post.getUser();
        if(!id.equals(user.getId())) {
            postRepository.delete(post);
        }
        return true;

    }
}
