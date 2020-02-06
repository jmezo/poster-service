package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
    @JsonIgnore
    private MultipartFile image;
}
