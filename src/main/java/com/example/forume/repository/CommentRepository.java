package com.example.forume.repository;

import com.example.forume.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long post);
    List<Comment> findAllByUserUsername(String userName);
}
