package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    private String senderName;
    private Chat chat;
    private LocalDateTime sendAt;
    private String text;

    public Message() {
    }

//    @ManyToOne(targetEntity = Chat.class)
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    public User getUser() {
//        return user;
//    }

    @Column(name = "sender_name", nullable = false)
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


//
//    public void setUser(User user) {
//        this.user = user;
//    }

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
