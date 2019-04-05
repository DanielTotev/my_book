package com.softuni.my_book.repository;

import com.softuni.my_book.domain.entities.FriendRequest;
import com.softuni.my_book.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {

    @Query("SELECT f.id FROM FriendRequest fr JOIN fr.user u JOIN fr.requestedFriend f WHERE u.id = :userId")
    List<String> getUserRequestedFriendsIds(String userId);

    @Query("SELECT u.id FROM FriendRequest fr JOIN fr.user u JOIN fr.requestedFriend rf WHERE rf.id = :userId")
    List<String> getPotentialFriendsIds(String userId);

    @Query("SELECT fr FROM FriendRequest fr JOIN fr.requestedFriend rf WHERE rf.username = :username")
    List<FriendRequest> getFriendRequestsForUserByUsername(String username);
}