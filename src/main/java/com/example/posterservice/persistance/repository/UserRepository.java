package com.example.posterservice.persistance.repository;


import com.example.posterservice.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

//    @Modifying
//    @Query("delete from User u where username = ?1")
    @Transactional
    long deleteByUsername(String username);

}
