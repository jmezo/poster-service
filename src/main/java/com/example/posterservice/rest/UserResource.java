package com.example.posterservice.rest;


import com.example.posterservice.persistance.model.DTO.UserCreateDTO;
import com.example.posterservice.persistance.model.DTO.UserDTO;
import com.example.posterservice.persistance.model.DTO.UserFollowDTO;
import com.example.posterservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@ModelAttribute UserCreateDTO dto) {
        return new ResponseEntity<>(
                userService.save(dto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{username}")
    public UserDTO findByUsername(@PathVariable String username) {
        return userService.get(username);
    }

    @GetMapping("/checkUsername/{username}")
    public boolean checkUsername(@PathVariable String username) {
        return userService.checkUsernameExists(username);
    }

    @GetMapping("/checkImage/{username}")
    public boolean checkImage(@PathVariable String username) {
        return userService.checkHasImage(username);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findALl();
    }

//    @PutMapping
//    public void update(@RequestBody User user) {
//        userService.save(user);
//    }
    @GetMapping("following/{username}")
    public List<String> getListOfFollowing(@PathVariable String username) {
        return userService.getListOfFollowing(username);
    }

    @PutMapping("{follower}/follow/{following}")
    public void follow(@PathVariable String follower, @PathVariable String following) {
        userService.follow(follower, following);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Long> delete(@PathVariable String username) {
        return new ResponseEntity<>(
                userService.deleteByUsername(username),
                HttpStatus.NO_CONTENT
        );
    }

}
