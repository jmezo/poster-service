package com.example.posterservice.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserImage {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn
    @MapsId
    private User user;

    private String contentType;

    private String originalFilename;

    @Lob
    private byte[] img;

    public UserImage(MultipartFile file, User user) throws IOException {
        this.user = user;
        this.contentType = file.getContentType();
        this.originalFilename = file.getOriginalFilename();
        this.img = file.getBytes();
    }

}
