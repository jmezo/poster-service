package com.example.posterservice.utils;


import com.example.posterservice.persistance.model.Comment;
import com.example.posterservice.persistance.model.Post;
import com.example.posterservice.persistance.model.Role;
import com.example.posterservice.persistance.model.User;
import com.example.posterservice.persistance.repository.CommentRepository;
import com.example.posterservice.persistance.repository.PostRepository;
import com.example.posterservice.persistance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class InitDataLoader {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder encoder;

    private List<User> users;
    private List<Post> posts;
    private List<Comment> comments;

    @PostConstruct
    public void init() {
        createUsers();
        createPosts();
        createComments();
    }

    private void createUsers() {
        List<User> testUsers = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            var user = User.builder()
                    .username("user"+i)
                    .password(encoder.encode("password"+i))
                    .CreationDate(System.currentTimeMillis())
                    .role(i == 0 ? Role.ADMIN : Role.USER)
                    .build();
            testUsers.add(user);
        }
        users = userRepository.saveAll(testUsers);
    }

    private void createPosts() {
        List<Post> testPosts = new ArrayList<>();

        for(int i = 0; i < users.size(); i++) {
            var post = Post.builder()
                    .creationDate(System.currentTimeMillis())
                    .creator(users.get(i))
                    .text(i + ". this is a random post by user: " + users.get(i).getUsername())
                    .build();
            testPosts.add(post);
        }
        posts = postRepository.saveAll(testPosts);
    }

    private void createComments() {
        List<Comment> testComments = new ArrayList<>();

        for(Post post: posts) {
            for(User user: users) {
                var comment = Comment.builder()
                        .post(post)
                        .commenter(user)
                        .text("this is a comment from: " + user.getUsername())
                        .creationDate(System.currentTimeMillis())
                        .build();
                testComments.add(comment);
            }
        }
        comments = commentRepository.saveAll(testComments);
    }
}
