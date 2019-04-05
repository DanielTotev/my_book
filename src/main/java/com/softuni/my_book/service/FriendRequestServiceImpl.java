package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.FriendRequest;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.FriendRequestServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.repository.FriendRequestRepository;
import com.softuni.my_book.service.contracts.FriendRequestService;
import com.softuni.my_book.service.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserService userService, ModelMapper mapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public boolean sendFriendRequest(UserServiceModel sender, UserServiceModel recipient) {
        try {
            FriendRequest request = new FriendRequest();
            request.setUser(this.mapper.map(sender, User.class));
            request.setRequestedFriend(this.mapper.map(recipient, User.class));
            this.friendRequestRepository.saveAndFlush(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getUserRequestedFriendsIds(String id) {
        return this.friendRequestRepository.getUserRequestedFriendsIds(id);
    }

    @Override
    public List<String> getPotentialFriendsIds(String userId) {
        return this.friendRequestRepository.getPotentialFriendsIds(userId);
    }

    @Override
    public List<FriendRequestServiceModel> getFriendRequestsForUser(String username) {
        return this.friendRequestRepository.getFriendRequestsForUserByUsername(username)
                .stream()
                .map(fr -> this.mapper.map(fr, FriendRequestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean acceptFriendRequest(String friendRequestId) {
        FriendRequest friendRequest =
                this.friendRequestRepository.findById(friendRequestId).orElse(null);

        try {
            if(friendRequest == null) {
                throw new IllegalArgumentException("No request with such id");
            }

            String senderId = friendRequest.getUser().getId();
            String recipientId = friendRequest.getRequestedFriend().getId();

            this.userService.makeFriends(senderId, recipientId);
            this.friendRequestRepository.delete(friendRequest);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean declineFriendRequest(String friendRequestId) {
        try {
            this.friendRequestRepository.deleteById(friendRequestId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
