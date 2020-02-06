package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeDTO {
    private String username;
    private Long postId;
}
