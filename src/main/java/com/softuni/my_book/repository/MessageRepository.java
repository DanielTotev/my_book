package com.softuni.my_book.repository;

import com.softuni.my_book.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> getAllByChatId(String chatId);

    List<Message> getAllByChatIdOrderBySendAtAsc(String chatId);

}
