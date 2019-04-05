package com.softuni.my_book.repository;

import com.softuni.my_book.domain.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query("SELECT c FROM Chat c JOIN c.firstUser fu JOIN c.secondUser su WHERE fu.username IN :usernames AND su.username IN :usernames")
    Optional<Chat> getChatByUsernames(String[] usernames);

}
