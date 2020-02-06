package com.example.posterservice.persistance.model.DTO;


import com.example.posterservice.persistance.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostGetDTO {
    private Long id;
    private String text;
    private boolean hasImage;
    private String url;
    private Long creationDate;
    private String creator;
    private Long numOfLikes;
    private Long numOfComments;

    public static PostGetDTO fromPost(Post post, Long numOfLikes, Long numOfComments) {
        return PostGetDTO.builder()
                .id(post.getId())
                .text(post.getText())
                .hasImage(post.getImage() != null)
                .url(post.getUrl())
                .creationDate(post.getCreationDate())
                .creator(post.getCreator().getUsername())
                .numOfLikes(numOfLikes)
                .numOfComments(numOfComments)
                .build();
    }
}
