package com.example.posterservice.persistance.model.DTO;


import com.example.posterservice.persistance.model.Role;
import com.example.posterservice.persistance.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private long timestampOfRegister;
    private Role role;

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getCreationDate(), user.getRole());
    }
}
