package com.supercoding.Project1.service;

import com.supercoding.Project1.entity.Comment;
import com.supercoding.Project1.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Transactional
    public Comment putComment(Comment comment) {
        logger.info("insert comment");
        comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
        comment.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Comment updatedComment) {
        logger.info("update comment");
        return commentRepository.findByIdAndPostIdAndEmail(updatedComment.getId(), updatedComment.getPostId(), updatedComment.getEmail() )
                .map(comment -> {
                    comment.setContent(updatedComment.getContent());
                    return commentRepository.save(comment);
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteComment(Long id) {
        logger.info("delete comment");
        if( commentRepository.findById(id) != null ) {
            commentRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional
    public List<Comment> getCommentsByPostId(Long postId) {
        logger.info("get comment by post");
        return commentRepository.findByPostId(postId);
    }

}