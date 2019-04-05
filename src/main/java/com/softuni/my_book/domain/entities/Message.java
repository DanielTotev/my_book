package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    private User user;
    private Chat chat;
    private LocalDateTime sendAt;
    private String text;

    public Message() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Chat.class)
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Column(name = "send_at", nullable = false)
    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
