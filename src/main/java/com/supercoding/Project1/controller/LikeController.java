package com.supercoding.Project1.controller;

import com.supercoding.Project1.entity.Like;
import com.supercoding.Project1.service.LikeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class LikeController {

    @Autowired
    LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @ApiOperation("Post 좋아요")
    @PostMapping("/likes/post")
    public ResponseEntity<Map<String,String>> likePost(@RequestBody Like like) {
        logger.info("Received request for /like/post");
        Map<String, String> response = new HashMap<>();
        Long postId = like.getPostId();
        String email = like.getEmail();

        if( postId == null || email == null ){
            response.put( "message", "필수 Parameter(post_id, email)를 입력해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            Like cur_like = likeService.likePost(like.getPostId(), like.getEmail());
            if( cur_like == null ){
                response.put("message", "해당 게시물에 좋아요를 누르지 못했습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            } else {
                response.put("message", "게시물에 좋아요를 눌렀습니다.");
                return ResponseEntity.ok(response);
            }
        }
        /*
        json 예시
        {
          "post_id": 1,
          "email": "peten@kakao.com"
        }
         */
    }

    @ApiOperation("댓글 좋아요")
    @PostMapping("/likes/comment")
    public ResponseEntity<Map<String,String>> likeComment(@RequestBody Like like) {
        logger.info("Received request for /like/comment");
        Map<String, String> response = new HashMap<>();
        Long postId = like.getPostId();
        Long commentId = like.getCommentId();
        String email = like.getEmail();

        if( postId == null || commentId == null || email == null ){
            response.put("message","필수 Parameter(post_id,comment_id,email)를 입력해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            Like cur_like = likeService.likeComment(like);
            if( cur_like == null ){
                response.put("message", "해당 댓글에 좋아요를 누르지 못했습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            } else {
                response.put("message", "댓글에 좋아요를 눌렀습니다.");
                return ResponseEntity.ok(response);
            }
        }

        /*
        json 예시
        {
          "post_id": 1,
          "comment_id": 2,
          "email": "peten@kakao.com"
        }
         */
    }

    @ApiOperation("Post 좋아요 취소")
    @PostMapping("/dislikes/post")
    public ResponseEntity<Map<String,String>> dislikePost(@RequestBody Like like) {
        logger.info("Received request for /dislikes/post");
        Map<String, String> response = new HashMap<>();
        Long postId = like.getPostId();
        String email = like.getEmail();
        boolean isDelete = false;

        if( postId == null || email == null ){
            response.put( "message", "필수 Parameter(post_id, email)를 입력해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            isDelete = likeService.dislikePost(like);
            if( isDelete == false ){
                response.put("message", "해당 게시물에 좋아요를 취소하지 못했습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            } else {
                response.put("message", "게시물에 좋아요가 취소되었습니다.");
                return ResponseEntity.ok(response);
            }
        }

        /*
        json 예시
        {
          "post_id": 1,
          "email": "peten@kakao.com"
        }
         */
    }

    @ApiOperation("댓글 좋아요 취소")
    @PostMapping("/dislikes/comment")
    public ResponseEntity<Map<String,String>> dislikeComment(@RequestBody Like like) {
        logger.info("Received request for /dislikes/comment");
        Map<String, String> response = new HashMap<>();
        Long postId = like.getPostId();
        Long commentId = like.getCommentId();
        String email = like.getEmail();
        boolean isDelete = false;

        if( postId == null || email == null || commentId == null){
            response.put( "message", "필수 Parameter(post_id, comment_id, email)를 입력해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            isDelete = likeService.dislikeComment(like);
            if( isDelete == false ){
                response.put("message", "해당 댓글에 좋아요를 취소하지 못했습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            } else {
                response.put("message", "댓글에 좋아요가 취소되었습니다.");
                return ResponseEntity.ok(response);
            }
        }

        /*
        json 예시
        {
          "post_id": 1,
          "comment_id": 2,
          "email": "peten@kakao.com"
        }
         */
    }

    @ApiOperation("Post 좋아요 개수 조회")
    @GetMapping("/likes/cnt_post")
    public ResponseEntity<Map<String,Long>> countLikesByPostId(@RequestParam Long post_id){
        logger.info("Received request for /like/cnt_post");
        Map<String, Long> response = new HashMap<>();
        Long count = likeService.countByPostId(post_id);
        response.put("like_count", count);
        return ResponseEntity.ok(response);
    }


    @ApiOperation("댓글 좋아요 개수 조회")
    @GetMapping("/likes/cnt_comment")
    public ResponseEntity<Map<String,Long>> countLikesByCommentId(@RequestParam Long post_id, @RequestParam Long comment_id){
        logger.info("Received request for /like/cnt_comment");
        Map<String, Long> response = new HashMap<>();
        Long count = likeService.countByPostIdAndCommentId(post_id,comment_id);
        response.put("like_count", count);
        return ResponseEntity.ok(response);
    }

}
