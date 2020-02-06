package com.example.posterservice.rest;


import com.example.posterservice.persistance.model.DTO.CommentCreateDTO;
import com.example.posterservice.persistance.model.DTO.CommentLikeDTO;
import com.example.posterservice.persistance.model.DTO.CommentGetDTO;
import com.example.posterservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreateDTO> createComment(@RequestBody CommentCreateDTO dto) {
        commentService.createComment(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<CommentGetDTO> getPostComments(@RequestParam long postId) {
        return commentService.getCommentsFromPost(postId);
    }

    @PutMapping("/like")
    public void likeComment(@RequestBody CommentLikeDTO dto) {
        commentService.likeComment(dto);

    }

    @PutMapping("/dislike")
    public void dislikeComment(@RequestBody CommentLikeDTO dto) {
        commentService.unlikeComment(dto);
    }
}
