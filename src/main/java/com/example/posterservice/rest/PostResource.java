package com.example.posterservice.rest;

import com.example.posterservice.persistance.model.DTO.PostCreateDTO;
import com.example.posterservice.persistance.model.DTO.PostCreateResponseDTO;
import com.example.posterservice.persistance.model.DTO.PostLikeDTO;
import com.example.posterservice.persistance.model.DTO.PostGetDTO;
import com.example.posterservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostResource {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponseDTO> createPost(@ModelAttribute PostCreateDTO dto) {
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.CREATED);
    }

    @PutMapping("/like")
    public void likePost(@RequestBody PostLikeDTO dto) {
        postService.likePost(dto);
    }

    @PutMapping("/dislike")
    public void dislikePost(@RequestBody PostLikeDTO dto) {
        postService.dislikePost(dto);
    }

    @GetMapping
    public List<PostGetDTO> getPosts(@RequestParam String username,
                                     @RequestParam(defaultValue = "false") boolean filterFollowing,
                                     @RequestParam(defaultValue = "DATE") PostService.SortBy sortBy) {
        System.out.println("PARAMS YALL: " + filterFollowing + sortBy);
        return postService.getPosts(username, filterFollowing, sortBy);
    }

    @GetMapping("/simple")
    public List<PostGetDTO> getSimple() {
        return postService.getPostsSimple();
    }

    @GetMapping("/ids")
    public List<Long> getPostIdsOrderedByDate() {
        return postService.getPostIdsOrderedByDate();
    }

    @GetMapping("{id}")
    public PostGetDTO getPostById(@PathVariable long id) {
        return postService.getPostById(id);
    }

    @GetMapping("likes/{id}")
    public List<String> getLikes(@PathVariable long id) {
        return postService.getLikes(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
