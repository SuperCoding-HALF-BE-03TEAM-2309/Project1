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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

//    public List<PostResponse> getAllPosts() {
//        List<Post> postList = postRepository.findAll();
//        return postList.stream().map(PostResponse::from).collect(Collectors.toList());
//    }

//    public Post writePost(AuthInfo of, String title, String content) {
//        UserEntity user = userRepository.findById(of.getMemberId()).orElseThrow(() -> new NoSuchElementException("User not found with memberId: " + of.getMemberId()));
//        return postRepository.save(
//                Post.builder()
//                        .user(user)
//                        .title(title)
//                        .content(content)
//                        .build()
//        );
//    }

    // 포스트 작성 구문
    @Transactional
    public Post save(PostRequest postRequest){
        return postRepository.save(postRequest.toEntity());
    }


//    public Post updatePost(Integer postId, PostRequest postRequest) {
//        Optional<Post> result = postRepository.findById(postId);
//
//        if(result.isPresent()){
//            Post entity = result.get();
//
//            entity.setTitle(postRequest.getTitle());
//            entity.setContent(postRequest.getContent());
//
//            postRepository.save(entity);
//        }
//        return Post.builder().build();
//    }
//
//    public boolean deletePostById(@PathVariable Integer postId) {
//        Post post = postRepository.getReferenceById(postId);
//            postRepository.delete(post);
//        return true;
//    }

    public List<PostResponse> list(AuthInfo of, String email) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postList = new ArrayList<>();

        for (Post post : posts){
            PostResponse postResponse = new PostResponse(post);
            postList.add(postResponse);
        }
        return postList;
    }

    public String update(Integer postId, PostRequest postRequest){
        String message = "fail";
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (post.getUser().getEmail().equals(postRequest.getUser().getEmail())){
            post.update(postRequest.getTitle(), postRequest.getContent());
            message = "suceess";
        }
        return message;
    }


    public String delete(Integer postId, UserEntity user) {
        String message = "fail";
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (post.getUser().getEmail().equals(user.getEmail())){
            postRepository.delete(post);
            message = "success";
        }
        return message;
    }

}
