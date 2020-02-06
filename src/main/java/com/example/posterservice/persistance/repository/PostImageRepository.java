package com.example.posterservice.persistance.repository;

import com.example.posterservice.persistance.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
