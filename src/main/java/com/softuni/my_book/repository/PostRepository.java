package com.softuni.my_book.repository;

import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> getAllByUploaderUsername(String username);

    @Query(value = "SELECT p.id, p.title, p.image_url, p.uploader_id FROM users u\n" +
            "JOIN users_friends uf on u.id = uf.user_id\n" +
            "JOIN posts p ON p.uploader_id=uf.friend_id\n" +
            "WHERE u.username = :username", nativeQuery = true)
    List<Post> getFriendsPosts(String username);
}