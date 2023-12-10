package com.supercoding.Project1.service;

import com.supercoding.Project1.entity.Comment;
import com.supercoding.Project1.repository.CommentRepository;
import com.supercoding.Project1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Transactional
    public Comment putComment(String email, Comment comment) {
        logger.info("insert comment");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
        comment.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(String email,Comment updatedComment) {
        logger.info("update comment");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        return commentRepository.findByIdAndPostIdAndEmail(updatedComment.getId(), updatedComment.getPostId(), updatedComment.getEmail() )
                .map(comment -> {
                    comment.setContent(updatedComment.getContent());
                    return commentRepository.save(comment);
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteComment(String email, Long id) {
        logger.info("delete comment");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        if( commentRepository.findByIdAndEmail(id, email) != null ) {
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