package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.FriendRequest;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.FriendRequestServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.repository.FriendRequestRepository;
import com.softuni.my_book.service.contracts.EmailService;
import com.softuni.my_book.service.contracts.FriendRequestService;
import com.softuni.my_book.service.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    private final EmailService emailService;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserService userService, ModelMapper mapper, EmailService emailService) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
        this.mapper = mapper;

        this.emailService = emailService;
    }

    @Override
    public boolean sendFriendRequest(UserServiceModel sender, UserServiceModel recipient) {
        try {
            FriendRequest request = new FriendRequest();
            request.setUser(this.mapper.map(sender, User.class));
            request.setRequestedFriend(this.mapper.map(recipient, User.class));
            request.setSendAt(LocalDate.now());
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

    @Scheduled(cron = "0 0 12 * * ?")
    public void notifyUserForRequest() {
        List<FriendRequest> friendRequests = this.friendRequestRepository
                .findAll()
                .stream()
                .filter(x -> x.getSendAt().equals(LocalDate.now()))
                .collect(Collectors.toList());

        friendRequests
                .forEach(friendRequest -> {
                    this.emailService.sendSimpleMessage(friendRequest.getRequestedFriend().getEmail(), "Friend Request", friendRequest.getUser().getUsername() + " send you a friend request!");
                });
    }
}
