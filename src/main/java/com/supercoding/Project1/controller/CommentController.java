package com.supercoding.Project1.controller;

import com.supercoding.Project1.entity.Comment;
import com.supercoding.Project1.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


    @ApiOperation("댓글 조회")
    @GetMapping("/comments")
    public ResponseEntity<Map<String,List<Comment>>> getCommentsByPostId(@RequestParam Long post_id) {
        logger.info("Received request for /comments(get)");
        Map<String, List<Comment>> response = new HashMap<>();
        List<Comment> comments = commentService.getCommentsByPostId(post_id);
        // TODO :: nickname 추가
        response.put("comments", comments);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("댓글 입력")
    @PostMapping("/comments")
    public ResponseEntity<Map<String,String>> insertComment(@RequestBody Comment comment){
        logger.info("Received request for /comments(post)");
        Map<String, String> response = new HashMap<>();
        Comment createComment = commentService.putComment(comment);
        if( createComment == null) {
            response.put("message", "댓글 작성이 실패되었습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            response.put("message", "댓글이 성공적으로 작성되었습니다.");
            return ResponseEntity.ok(response);
        }

        /*
        json 예시
        {
          "post_id": 1,
          "email": "peten@kakao.com",
          "content": "This is a new comment"
        }
         */
    }

    @ApiOperation("댓글 수정")
    @PutMapping("/comments")
    public ResponseEntity<Map<String,String>> updateComment(@RequestBody Comment request){
        logger.info("Received request for /comments(put)");
        Long id = request.getId();
        Long postId = request.getPostId();
        String email = request.getEmail();
        String content = request.getContent();


        Map<String, String> response = new HashMap<>();
        if( id == null || postId == null || email == null || content == null ){
            response.put("message","필수 Parameter(id,post_id,email,content)를 입력해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            Comment updateComment = commentService.updateComment(request);
            if (updateComment != null) {
                response.put("message", "댓글이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "댓글 수정이 실패되었습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            }
        }

        /*
        json 예시
        {
          "id": 1,
          "post_id": 1,
          "email": "peten@kakao.com",
          "content": "This is a new comment"
        }
         */
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping ("/comments")
    public ResponseEntity<Map<String,String>> deleteComment(@RequestBody Comment comment) {
        logger.info("Received request for /comments(delete)");
        Map<String, String> response = new HashMap<>();
        Long commandId = comment.getId();
        if( commandId == null ){
            response.put("message", "id가 입력되지 않았습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            boolean isDelete = commentService.deleteComment(comment.getId());
            if (isDelete == true) {
                response.put("message", "댓글이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "댓글 삭제가 실패되었습니다.");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
            }
        }

        /*
        json 예시
        {
          "id": 1
        }
         */
    }
}
