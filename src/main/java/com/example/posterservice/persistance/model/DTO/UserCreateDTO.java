package com.example.posterservice.persistance.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
}
