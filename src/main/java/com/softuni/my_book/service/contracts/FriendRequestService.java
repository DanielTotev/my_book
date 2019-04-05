package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.FriendRequestServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;

import java.util.List;

public interface FriendRequestService {
    boolean sendFriendRequest(UserServiceModel sender, UserServiceModel recipient);

    List<String> getUserRequestedFriendsIds(String id);

    List<String> getPotentialFriendsIds(String userId);

    List<FriendRequestServiceModel> getFriendRequestsForUser(String username);

    boolean acceptFriendRequest(String friendRequestId);

    boolean declineFriendRequest(String friendRequestId);
}
