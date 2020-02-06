package com.example.posterservice.persistance.repository;


import com.example.posterservice.persistance.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p.id from Post p order by p.creationDate desc")
    List<Long> findAllPostIds();

}
