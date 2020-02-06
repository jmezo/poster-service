package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCreateDTO {
    private long postId;
    private String username;
    private String text;
}
