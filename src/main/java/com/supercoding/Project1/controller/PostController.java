package com.supercoding.Project1.controller;

import com.supercoding.Project1.dto.AuthInfo;
import com.supercoding.Project1.dto.PostRequest;
import com.supercoding.Project1.dto.PostResponse;
import com.supercoding.Project1.entity.Post;
import com.supercoding.Project1.entity.UserEntity;
import com.supercoding.Project1.service.PostService;
import com.supercoding.Project1.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation("새 post 작성")
    @PostMapping("/create")
    public String create(@RequestBody PostRequest postRequest){ // @RequestParam String userEmail
        // ToDo : postRequest.setUser(); // 유저 구분하기...
        Post post = postService.save(postRequest);

        return "success";
    }

//    public ResponseEntity<PostResponse> writePost(AuthInfo authInfo, @RequestBody PostRequest postRequest){
//        Post post = postService.writePost(AuthInfo.of(authInfo.getMemberId()), postRequest.getTitle(), postRequest.getContent());
//        return ResponseEntity.ok(PostResponse.from(post));
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/save")
//    public void save(@ModelAttribute PostRequest postRequest){
//        postService.save(postRequest);
//    }

//    @ApiOperation("모든 post 조회")
//    @GetMapping("/read")
//    public List<PostResponse> getAllPosts(){
//        return postService.getAllPosts()
//                .stream()
//                .map(PostResponse::from)
//                .collect(Collectors.toList());
//    }

    @GetMapping("/posts")
    public List<PostResponse> list(AuthInfo authInfo, @RequestParam(value = "email", required = false) String email){
        return postService.list(AuthInfo.of(authInfo.getMemberId()), email);
    }

//    @ApiOperation("post 수정")
//    @PutMapping("/posts/{post_id}")
//    public Post updatePost(AuthInfo authInfo, @PathVariable Integer post_id, @RequestBody PostRequest postRequest){
//        return postService.updatePost(post_id, postRequest);
//    }
//
//    @ApiOperation("post 삭제")
//    @DeleteMapping("/posts/{post_id}")
//    public String deletePostById(AuthInfo authInfo, Integer post_id){
//        postService.deletePostById(authInfo.getMemberId(), post_id);
//        return "Post with id = " + post_id + " has been deleted";
//    }

    @PutMapping("/update/{post_id}")
    public String update(@PathVariable Integer post_id, @RequestBody PostRequest postRequest){ // @AuthenticationPrincipal SecurityUser principal
        // ToDo : 유저 구분하기 postRequest.setUser();
        return postService.update(post_id, postRequest);
    }

    @DeleteMapping("/delete/{post_id}")
    public String delete(@PathVariable Integer post_id, @AuthenticationPrincipal UserEntity user){
        return postService.delete(post_id,user);
    }



}
