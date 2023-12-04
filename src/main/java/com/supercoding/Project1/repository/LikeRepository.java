package com.supercoding.Project1.repository;

import com.supercoding.Project1.entity.Like;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EntityScan
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    List<Like> findByEmailAndPostIdAndType(String email, Long postId, Integer type);

    void deleteByEmailAndPostIdAndType(String email, Long postId, Integer type);

    void deleteByEmailAndPostIdAndCommentIdAndType(String email, Long postId, Long commentId, Integer type);

    Long countByPostIdAndType(Long postId, Integer type);

    Long countByPostIdAndCommentIdAndType(Long postId, Long commentId, Integer type);

    List<Like> findByEmailAndPostIdAndCommentIdAndType(String email, Long postId, Long commentId, Integer type);

    List<Like> findByEmailAndCommentIdAndType(String email, Long commentId, Integer type);
}
