package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend_requests")
public class FriendRequest extends BaseEntity {
    private User user;
    private User requestedFriend;

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
}
