package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat extends BaseEntity {
    private User firstUser;
    private User secondUser;
    private List<Message> messagesSend;

    public Chat() {
    }


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "first_user_id", nullable = false, referencedColumnName = "id")
    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "second_user_id", nullable = false, referencedColumnName = "id")
    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    @OneToMany(targetEntity = Message.class, mappedBy = "chat")
    public List<Message> getMessagesSend() {
        return messagesSend;
    }

    public void setMessagesSend(List<Message> messagesSend) {
        this.messagesSend = messagesSend;
    }
}
