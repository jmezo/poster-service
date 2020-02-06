package com.example.posterservice.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private long CreationDate;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Post> postsCreated;

    @ManyToMany
    private List<User> following;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // for initDataLoader
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addPost(Post post) {
        if (postsCreated == null) {
            postsCreated = new ArrayList<>();
        }
        if (!postsCreated.contains(post)) {
            postsCreated.add(post);
        }
    }

    public void removePost(Post post) {
        if (postsCreated != null) {
            postsCreated.remove(post);
        }
    }

    public void addFollowing(User user) {
        if (following == null) {
            following = new ArrayList<>();
        }
        if (!following.contains(user)) {
            following.add(user);
        }
    }

    public void removeFollowing(User user) {
        if (following != null && user != null) {
            following.remove(user);
        }
    }
}
