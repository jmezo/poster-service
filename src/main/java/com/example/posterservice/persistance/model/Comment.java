package com.example.posterservice.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User commenter;

    @Column(length = 200)
    private String text;

    private Long creationDate;

    @ManyToMany
    private List<User> likes;

    public Comment(Post post, User commenter, String text) {
        this.post = post;
        this.commenter = commenter;
        this.text = text;
        this.creationDate = System.currentTimeMillis();
    }

    public void addLike(User user) {
        if (likes == null) {
            likes = new ArrayList<>();
        }
        if (!likes.contains(user)) {
            likes.add(user);
        }
    }

    public void removeLike(User user) {
        if (likes != null) {
            likes.remove(user);
        }
    }
}
