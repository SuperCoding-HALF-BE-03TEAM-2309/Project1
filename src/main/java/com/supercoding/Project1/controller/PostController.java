package com.supercoding.Project1.controller;

import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @ApiOperation("새 포스트 입력")
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> savePost(AuthInfo authInfo, @RequestBody Post post) {
        logger.info("/create(post 작성 요청)");
        Map<String, String> response = new HashMap<>();
        Post newPost = postService.savePost(authInfo, post);
        if (newPost == null) {
            response.put("message", "게시물 작성이 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            response.put("message", "게시물이 성공적으로 작성되었습니다.");
            return ResponseEntity.ok(response);
        }
    }

    @ApiOperation("모든 포스트 조회")
    @GetMapping("/posts")
    public List<PostResponse> allPost(AuthInfo authInfo){
        logger.info("/posts(post 조회 요청");
        return postService.allPost();
        }

    @ApiOperation("포스트 수정")
    @PutMapping("/update/{post_id}")
    public ResponseEntity<Map<String, String>> updatePost(
            @PathVariable("post_id") Long postId,
            @RequestBody PostRequest postRequest) {

        Post updatedPost = postService.updatePost(postId, postRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물이 수정되었습니다.");
        response.put("postId", updatedPost.getPostId().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("포스트 삭제")
    @DeleteMapping("/delete/{post_id}")
    public String deletePost(@PathVariable("post_id") Long postId){
        return postService.deletePost(postId);
    }


}

