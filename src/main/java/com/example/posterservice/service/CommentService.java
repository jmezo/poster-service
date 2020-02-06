package com.example.posterservice.service;


import com.example.posterservice.persistance.model.Comment;
import com.example.posterservice.persistance.model.DTO.CommentCreateDTO;
import com.example.posterservice.persistance.model.DTO.CommentLikeDTO;
import com.example.posterservice.persistance.model.DTO.CommentGetDTO;
import com.example.posterservice.persistance.model.Post;
import com.example.posterservice.persistance.model.User;
import com.example.posterservice.persistance.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No comment with id of " + id));
    }

    public void createComment(CommentCreateDTO dto) {
        User commenter = userService.findByUsername(dto.getUsername());
        Post post = postService.findById(dto.getPostId());
        Comment comment = new Comment(post, commenter, dto.getText());
        commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.delete(findById(id));
    }

    public List<CommentGetDTO> getCommentsFromPost(long postId) {
        // TODO sort by time
        List<Comment> comments = commentRepository.findByPostIdOrderByCreationDateDesc(postId);

        return comments.stream().map(c -> {
            String commenter = c.getCommenter().getUsername();
            int likes = c.getLikes().size();
            return CommentGetDTO.fromComment(c, commenter, likes);
        }).collect(Collectors.toList());
    }

    public void likeComment(CommentLikeDTO dto) {
        Comment comment = findById(dto.getCommentId());
        User user = userService.findByUsername(dto.getUsername());
        comment.addLike(user);
        commentRepository.save(comment);
    }

    public void unlikeComment(CommentLikeDTO dto) {
        Comment comment = findById(dto.getCommentId());
        User user = userService.findByUsername(dto.getUsername());
        comment.removeLike(user);
        commentRepository.save(comment);
    }
}
