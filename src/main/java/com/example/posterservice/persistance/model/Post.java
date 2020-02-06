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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String text;


    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PostImage image;

    private String url;

    private Long creationDate;

    @ManyToOne
    private User creator;

    @ManyToMany()
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<User> likedBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void addLike(User user) {
        if(likedBy == null) {
            likedBy = new ArrayList<>();
        }
        if (!likedBy.contains(user)) {
            likedBy.add(user);
        }
    }

    public void removeLike(User user) {
        if(likedBy != null) {
            likedBy.remove(user);
        }
    }

    public void adComment(Comment comment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }
        if (!comments.contains(comment)) {
            comments.add(comment);
        }
    }

    public void removeComment(Comment comment) {
        if(comments != null) {
            comments.remove(comment);
        }
    }

    public Post(User creator, String text) {
        this.creator = creator;
        this.text = text;
        this.creationDate = System.currentTimeMillis();
    }
}
