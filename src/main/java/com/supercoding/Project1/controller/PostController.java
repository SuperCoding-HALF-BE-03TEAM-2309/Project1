package com.supercoding.Project1.controller;

import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation("새 post 작성")
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> writePost(AuthInfo authInfo, @RequestBody PostRequest postRequest){
        Post post = postService.writePost(postRequest.getTitle(), postRequest.getContent(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(post));

    }

    @ApiOperation("모든 post 조회")
    @GetMapping("/posts")
    public List<PostResponse> getAllPosts(AuthInfo authInfo, @RequestBody PostResponse postResponse){
        return ResponseEntity.ok(
                postService.getAllPosts().stream()
                        .map(postResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation("post 수정")
    @PutMapping("/posts/{post_id}")
    public Post updatePost(AuthInfo authInfo, @PathVariable Integer post_id, @RequestBody PostRequest postRequest){
        return postService.updatePost(post_id, postRequest);


    }

    @ApiOperation("post 삭제")
    @DeleteMapping("/posts/{post_id}")
    public void deletePostById(AuthInfo authInfo, Integer post_id){
        postService.deletePostById(post_id);
        return post_id + "post deleted";

    }



}
