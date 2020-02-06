package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentLikeDTO {
    private long commentId;
    private String username;
}
