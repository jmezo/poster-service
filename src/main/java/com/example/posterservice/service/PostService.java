package com.example.posterservice.service;


import com.example.posterservice.persistance.model.DTO.PostCreateDTO;
import com.example.posterservice.persistance.model.DTO.PostCreateResponseDTO;
import com.example.posterservice.persistance.model.DTO.PostLikeDTO;
import com.example.posterservice.persistance.model.DTO.PostGetDTO;
import com.example.posterservice.persistance.model.Post;
import com.example.posterservice.persistance.model.User;
import com.example.posterservice.persistance.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    public enum SortBy {
        DATE, LIKES
    }

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostImageService imageService;

    Post findById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No post with id of " + id));
    }

    public PostCreateResponseDTO createPost(PostCreateDTO dto) {
        User creator = userService.findByUsername(dto.getUsername());
        Post post = PostCreateDTO.toPost(dto, creator);
        if (dto.getImage() != null) {
            imageService.saveWithPost(dto.getImage(), post);
        }
        var savedPost = postRepository.save(post);
        return new PostCreateResponseDTO(savedPost);
    }

    public void deletePost(long id) {
        postRepository.delete(findById(id));
    }

    public void likePost(PostLikeDTO dto) {
        User user = userService.findByUsername(dto.getUsername());
        Post post = findById(dto.getPostId());
        post.addLike(user);
        postRepository.save(post);
    }

    public void dislikePost(PostLikeDTO dto) {
        User user = userService.findByUsername(dto.getUsername());
        Post post = findById(dto.getPostId());
        post.removeLike(user);
        postRepository.save(post);
    }

    public List<Long> getPostIdsOrderedByDate() {
        return postRepository.findAllPostIds();
    }

    public PostGetDTO getPostById(long id) {
        var post = findById(id);
        return PostGetDTO.fromPost(post, (long) post.getLikedBy().size(), (long) post.getComments().size());
    }

    public List<PostGetDTO> getPostsSimple() {
        return postRepository.findAll().stream()
                .map(post -> {
                    long numOfLikes = post.getLikedBy().size();
                    long numOfComments = post.getComments().size();
                    return PostGetDTO.fromPost(post, numOfLikes, numOfComments);
                })
                .sorted(Comparator.comparingLong(PostGetDTO::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    public List<PostGetDTO> getPosts(String username, boolean filterFollowing, SortBy sortBy) {
        List<Post> posts = postRepository.findAll();
        if (filterFollowing) {
            final List<String> namesOfFollowing = userService.findByUsername(username)
                    .getFollowing().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());

            posts = posts.stream()
                    .filter(p -> namesOfFollowing.contains(p.getCreator().getUsername()))
                    .collect(Collectors.toList());
        }
        List<PostGetDTO> postDTOS = posts.stream().map(p -> {
            long count = p.getLikedBy().size();
            return PostGetDTO.fromPost(p, count, 0L);
        }).collect(Collectors.toList());

        switch (sortBy) {
            case DATE:
                postDTOS.sort((p1, p2) -> (int) (p2.getCreationDate() - p1.getCreationDate()));
                break;
            case LIKES:
                postDTOS.sort((p1, p2) -> {
                    int likeDiff = (int) (p2.getNumOfLikes() - p1.getNumOfLikes());
                    return likeDiff != 0 ? likeDiff : (int) (p2.getCreationDate() - p1.getCreationDate());
                });
        }
        return postDTOS;
    }

    public List<String> getLikes(long postId) {
        return findById(postId).getLikedBy().stream()
                .map(User::getUsername).collect(Collectors.toList());
    }

}
