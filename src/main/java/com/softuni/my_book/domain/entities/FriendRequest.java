package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "friend_requests")
public class FriendRequest extends BaseEntity {
    private User user;
    private User requestedFriend;
    private LocalDate sendAt;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "requested_friend_id", referencedColumnName = "id", nullable = false)
    public User getRequestedFriend() {
        return requestedFriend;
    }

    public void setRequestedFriend(User requestedFriend) {
        this.requestedFriend = requestedFriend;
    }

    @Column(name = "send_at", nullable = false)
    public LocalDate getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDate sendAt) {
        this.sendAt = sendAt;
    }
}
