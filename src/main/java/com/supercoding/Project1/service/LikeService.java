package com.supercoding.Project1.service;

import com.supercoding.Project1.entity.Like;
import com.supercoding.Project1.repository.LikeRepository;
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
    public Like likePost(Long postId, String email) {
        logger.info("like by post");
        Like like = new Like();
        like.setPostId(postId);
        like.setEmail(email);
        like.setType(1); // type을 1로 설정시 Post
        return likeRepository.save(like);
    }

    @Transactional
    public Like likeComment(Like like) {
        logger.info("like by comment");
        like.setType(2); //  type을 2로 설정시 Comment
        return likeRepository.save(like);
    }

    @Transactional
    public boolean dislikePost(Like unlike) {
        logger.info("dislike post");
        List<Like> list = likeRepository.findByEmailAndPostIdAndType(unlike.getEmail(), unlike.getPostId(), 1);
        if( list.size() > 0 ){
            likeRepository.deleteByEmailAndPostIdAndType(unlike.getEmail(), unlike.getPostId(), 1);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean dislikeComment(Like unlike) {
        logger.info("dislike comment");
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
}
