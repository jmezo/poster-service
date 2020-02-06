package com.example.posterservice.service;

import com.example.posterservice.persistance.model.User;
import com.example.posterservice.persistance.model.UserImage;
import com.example.posterservice.persistance.repository.UserImageRepository;
import com.example.posterservice.persistance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@AllArgsConstructor
public class UserImageService {

    private final UserImageRepository imageRepository;
    private final UserRepository userRepository;


    public void storeImage(MultipartFile file, String username) {
        var post = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"user not found"));
        saveWithUser(file, post);
    }

    public UserImage getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "image not found"));
    }

    UserImage saveWithUser(MultipartFile file, User user) {
        try {
            var image = new UserImage(file, user);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image not too large");
        }
    }
}
