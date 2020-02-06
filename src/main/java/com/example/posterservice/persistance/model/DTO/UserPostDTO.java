package com.example.posterservice.persistance.model.DTO;


import com.example.posterservice.persistance.model.User;
import lombok.Data;

@Data
public class UserPostDTO {
    private String username;

    public UserPostDTO(User user) {
        this.username = user.getUsername();
    }
}
