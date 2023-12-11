package com.supercoding.Project1.service;

import com.supercoding.Project1.entity.Like;
import com.supercoding.Project1.repository.LikeRepository;
import com.supercoding.Project1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    @Transactional
    public List<Like> getLikesByEmailAndPostId(String email, Long postId ) {
        logger.info("get likes by email & postId & type = 1");
        return likeRepository.findByEmailAndPostIdAndType(email, postId, 1 );
    }

    @Transactional
    public List<Like> getLikesByEmailAndCommentId(String email, Long commentId) {
        logger.info("get likes by email & commentId & type = 2");
        return likeRepository.findByEmailAndCommentIdAndType(email, commentId, 2);
    }

    @Transactional
    public Like likePost(String authEmail, Long postId, String email) {
        logger.info("like by post");
        Like like = new Like();
        userRepository.findByEmail(authEmail).orElseThrow( IllegalAccessError::new);
        if( authEmail.equals(email) == false ){
            return null;
        }
        like.setPostId(postId);
        like.setEmail(email);
        like.setType(1); // type을 1로 설정시 Post
        return likeRepository.save(like);
    }

    @Transactional
    public Like likeComment(String email, Like like) {
        logger.info("like by comment");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        if( email.equals(like.getEmail()) == false) {
            return null;
        }
        like.setType(2); //  type을 2로 설정시 Comment
        return likeRepository.save(like);
    }

    @Transactional
    public boolean dislikePost(String email, Like unlike) {
        logger.info("dislike post");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        if( email.equals(unlike.getEmail()) == false) {
            return false;
        }
        List<Like> list = likeRepository.findByEmailAndPostIdAndType(unlike.getEmail(), unlike.getPostId(), 1);
        if( list.size() > 0 ){
            likeRepository.deleteByEmailAndPostIdAndType(unlike.getEmail(), unlike.getPostId(), 1);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean dislikeComment(String email, Like unlike) {
        logger.info("dislike comment");
        userRepository.findByEmail(email).orElseThrow( IllegalAccessError::new);
        if( email.equals(unlike.getEmail()) == false){
            return false;
        }

        List<Like> list = likeRepository.findByEmailAndPostIdAndCommentIdAndType(unlike.getEmail(), unlike.getPostId(), unlike.getCommentId(), 2);
        if( list.size() > 0 ){
            likeRepository.deleteByEmailAndPostIdAndCommentIdAndType(unlike.getEmail(), unlike.getPostId(), unlike.getCommentId(), 2);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Long countByPostId(Long postId) {
        logger.info("count by post");
        return likeRepository.countByPostIdAndType(postId, 1);
    }

    @Transactional
    public Long countByPostIdAndCommentId(Long postId, Long commentId){
        logger.info("count by comment");
        return likeRepository.countByPostIdAndCommentIdAndType(postId, commentId, 2);
    }

    public List<Like> findLikePost(String authEmail, Long postId, String email) {
        logger.info("find like(post)");
        userRepository.findByEmail(authEmail).orElseThrow( IllegalAccessError::new);
        if( authEmail.equals(email) == false){
            return null;
        }
        return likeRepository.findByEmailAndPostIdAndType(email,postId,1);
    }

    public List<Like> findLikeComment(String authEmail, Long postId, Long commentId, String email) {
        logger.info("find like(comment)");
        userRepository.findByEmail(authEmail).orElseThrow( IllegalAccessError::new);
        if( authEmail.equals(email) == false){
            return null;
        }
        return likeRepository.findByEmailAndPostIdAndCommentIdAndType(email, postId, commentId, 2 );
    }
}
