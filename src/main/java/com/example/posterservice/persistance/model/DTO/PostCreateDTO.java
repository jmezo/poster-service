package com.example.posterservice.persistance.model.DTO;

import com.example.posterservice.persistance.model.Post;
import com.example.posterservice.persistance.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDTO {
    private String username;
    private String text;
    private String url;

    @JsonIgnore
    private MultipartFile image;

    public static Post toPost(PostCreateDTO dto, User creator) {
        return Post.builder()
                .creator(creator)
                .text(dto.getText())
                .url(dto.getUrl())
                .creationDate(System.currentTimeMillis())
                .build();
    }

}
