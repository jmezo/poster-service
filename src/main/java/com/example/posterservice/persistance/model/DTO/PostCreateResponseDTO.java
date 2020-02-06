package com.example.posterservice.persistance.model.DTO;

import com.example.posterservice.persistance.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateResponseDTO {
    private Long id;
    private String text;
    private boolean hasImage;
    private String url;
    private Long creationDate;
    private String creator;

    public PostCreateResponseDTO(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.hasImage = post.getImage() != null;
        this.url = post.getUrl();
        this.creationDate = post.getCreationDate();
        this.creator = post.getCreator().getUsername();
    }

}
