package com.example.posterservice.persistance.repository;

import com.example.posterservice.persistance.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
