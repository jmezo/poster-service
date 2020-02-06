package com.example.posterservice.rest;

import com.example.posterservice.service.UserImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users/img")
@AllArgsConstructor
public class UserImageResource {

    private final UserImageService imageService;


    @PostMapping("{username}")
    public void createImage(@ModelAttribute MultipartFile file, @PathVariable String username) {
        imageService.storeImage(file, username);

    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getImage(@PathVariable long id) {
        var dbImage = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbImage.getContentType()))
                .body(new ByteArrayResource(dbImage.getImg()));
    }
}
