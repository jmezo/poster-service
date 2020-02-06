package com.example.posterservice.persistance.model.DTO;


import com.example.posterservice.persistance.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDTO {
    private Long id;
    private Long postId;
    private String commenter;
    private String text;
    private long creationDate;
    private int numOfLikes;

    public static CommentGetDTO fromComment(Comment comment, String commenter, int numOfLikes) {
        return CommentGetDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .commenter(commenter)
                .text(comment.getText())
                .creationDate(comment.getCreationDate())
                .numOfLikes(numOfLikes)
                .build();
    }
}
