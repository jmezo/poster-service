package com.example.posterservice.service;


import com.example.posterservice.persistance.model.DTO.UserCreateDTO;
import com.example.posterservice.persistance.model.DTO.UserDTO;
import com.example.posterservice.persistance.model.Role;
import com.example.posterservice.persistance.model.User;
import com.example.posterservice.persistance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserImageService imageService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTO save(UserCreateDTO dto) {
        if (checkUsernameExists(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username taken");

        }
        if(dto.getUsername() == null || dto.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "required username and password");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreationDate(System.currentTimeMillis());

        if(dto.getImage() != null) {
            imageService.saveWithUser(dto.getImage(), user);
        }

        user.setRole(Role.USER);
        var savedUser = userRepository.save(user);
        return UserDTO.fromUser(savedUser);
    }

    public User findByUsername(String username) {
        return userRepository.findFirstByUsername(username).orElseThrow();
    }

    public UserDTO get(String username) {
        var user = userRepository.findFirstByUsername(username).orElseThrow();
        return UserDTO.fromUser(user);
    }

    public long deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    public List<UserDTO> findALl() {
        return userRepository.findAll().stream().map(UserDTO::fromUser).collect(Collectors.toList());
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.findFirstByUsername(username).orElse(null) != null;
    }

    public boolean checkHasImage(String username) {
        return userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"))
                .getImage() != null;
    }
}
