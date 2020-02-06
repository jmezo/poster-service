package com.example.posterservice.service;

import com.example.posterservice.persistance.model.Post;
import com.example.posterservice.persistance.model.PostImage;
import com.example.posterservice.persistance.repository.PostImageRepository;
import com.example.posterservice.persistance.repository.PostRepository;
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
public class PostImageService {

    private final PostImageRepository imageRepository;
    private final PostRepository postRepository;

    public void storeImage(MultipartFile file, long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"post not found"));
        saveWithPost(file, post);
    }

    public PostImage getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "image not found"));
    }

    private byte[] handleImg(MultipartFile img) throws IOException {
        return img.getBytes();
    }

    PostImage saveWithPost(MultipartFile file, Post post) {
        try {
            var image = new PostImage(file, post);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image too large");
        }
    }


}
