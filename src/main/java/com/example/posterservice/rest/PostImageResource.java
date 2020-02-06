package com.example.posterservice.rest;

import com.example.posterservice.persistance.model.DTO.PostCreateDTO;
import com.example.posterservice.service.PostImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("posts/img")
@AllArgsConstructor
public class PostImageResource {

    private final PostImageService imageService;


    @PostMapping("{id}")
    public void createImage(@ModelAttribute MultipartFile file, @PathVariable long id) {
        imageService.storeImage(file, id);

    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> getImage(@PathVariable long id) {
        var dbImage = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbImage.getContentType()))
                .body(new ByteArrayResource(dbImage.getImg()));
    }


}
