package com.example.posterservice.persistance.repository;


import com.example.posterservice.persistance.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPostIdOrderByCreationDateDesc(long postId);
}
