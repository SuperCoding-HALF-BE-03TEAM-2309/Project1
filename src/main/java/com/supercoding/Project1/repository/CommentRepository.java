package com.supercoding.Project1.repository;

import com.supercoding.Project1.entity.Comment;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EntityScan
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);

    Optional<Comment> findByIdAndPostIdAndEmail(Long id, Long postId, String email);

}
