package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFollowDTO {
    private String followerUsername;
    private String followingUsername;
}
