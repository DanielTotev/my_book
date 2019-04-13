package com.softuni.my_book.integration.services;

import com.softuni.my_book.domain.entities.Chat;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.ChatServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.chat.ChatNotFoundException;
import com.softuni.my_book.errors.user.UserNotFoundException;
import com.softuni.my_book.repository.ChatRepository;
import com.softuni.my_book.service.ChatServiceImpl;
import com.softuni.my_book.service.contracts.ChatService;
import com.softuni.my_book.service.contracts.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatServiceTests {

    @MockBean
    private ChatRepository mockChatRepository;

    @MockBean
    private UserService mockUserService;

    @Autowired
    private ModelMapper mapper;

    private ChatService chatService;

    private List<UserServiceModel> users;
    private List<Chat> chats;

    @Before
    public void before() {
        this.chatService = new ChatServiceImpl(this.mockChatRepository, this.mockUserService, this.mapper);
        UserServiceModel firstUser = new UserServiceModel();
        firstUser.setId("123");
        firstUser.setUsername("User 1");
        firstUser.setPassword("pass");
        firstUser.setEmail("email1");

        UserServiceModel secondUser = new UserServiceModel();
        secondUser.setId("456");
        secondUser.setUsername("User 2");
        secondUser.setPassword("pass2");
        secondUser.setEmail("email2");

        Chat chat = new Chat();
        chat.setId("1234");
        chat.setFirstUser(this.mapper.map(firstUser, User.class));
        chat.setSecondUser(this.mapper.map(secondUser, User.class));

        this.chats = List.of(chat);
        this.users = List.of(firstUser, secondUser);

        Mockito.when(this.mockUserService.findByUsername(Mockito.anyString()))
                .thenAnswer((Answer<UserServiceModel>) invocation -> {
                    Object[] args = invocation.getArguments();
                    String username = (String) args[0];
                    return users.stream().filter(x -> x.getUsername().equals(username)).findFirst().orElseThrow(UserNotFoundException::new);
                });

        Mockito.when(this.mockChatRepository.saveAndFlush(Mockito.any()))
                .thenAnswer((Answer<Chat>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (Chat) args[0];
                });

        Mockito.when(this.mockChatRepository.findById(Mockito.any()))
                .thenAnswer((Answer<Optional<Chat>>) invocation -> {
                    Object[] args = invocation.getArguments();
                    String id = (String) args[0];
                    return this.chats.stream().filter(x -> x.getId().equals(id)).findFirst();
                });

//        Mockito.when(this.mockChatRepository.getChatByUsernames(Mockito.any()))
//                .thenAnswer((Answer<Chat>) invocation -> {
//                    Object[] args = invocation.getArguments();
//                    String[] usernames = (String[]) args[0];
//                    List<String> usernamesAsList = List.of(usernames);
//                    return this.chats.stream().filter(ch -> {
//                        String firstUserUsername = ch.getFirstUser().getUsername();
//                        String secondUserUsername = ch.getSecondUser().getUsername();
//                        return usernamesAsList.contains(firstUserUsername) && usernamesAsList.contains(secondUserUsername);
//                    }).findFirst().orElse(null);
//                });

    }

    @Test
    public void createChat_withValidUsers_expectCorrectOutcome() {
        String firstUsername = "User 1";
        String secondUsername = "User 2";

        ChatServiceModel chatServiceModel = this.chatService.saveChat(firstUsername, secondUsername);

        Assert.assertEquals(firstUsername, chatServiceModel.getFirstUser().getUsername());
        Assert.assertEquals(secondUsername, chatServiceModel.getSecondUser().getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void createChat_withNonExistingUsers_expectException() {
        String firstUsername = "User0";
        String secondUsername = "User 2";

        this.chatService.saveChat(firstUsername, secondUsername);
    }

    @Test
    public void findChatByUsernames_withCorrectData_expectCorrectResult() {
        String firstUsername = "User 1";
        String secondUsername = "User 2";
        setMock(new String[]{firstUsername, secondUsername});

        ChatServiceModel chatServiceModel = this.chatService.findByUsernames(new String[]{firstUsername, secondUsername});

        Assert.assertNotNull(chatServiceModel);
    }

    @Test
    public void findByUsernames_withIncorrectData_expectNull(){
        String firstUsername = "User 4";
        String secondUsername = "User 5";
        setMock(new String[]{firstUsername, secondUsername});

        ChatServiceModel chatServiceModel = this.chatService.findByUsernames(new String[]{firstUsername, secondUsername});

        Assert.assertNull(chatServiceModel);
    }

    @Test
    public void findById_withCorrectId_expectChat() {
        String id = "1234";
        ChatServiceModel chat = this.chatService.findById(id);
        Assert.assertEquals(chat.getId(), id);
    }

    @Test(expected = ChatNotFoundException.class)
    public void findById_withIncorrectId_expectException() {
        String id = "123456";
        this.chatService.findById(id);
    }

    private void setMock(String[] usernames) {
        List<String> usernamesAsList = List.of(usernames);
        if(this.chats.stream().anyMatch(x -> usernamesAsList.contains(x.getFirstUser().getUsername())
                && usernamesAsList.contains(x.getSecondUser().getUsername()))){
            Mockito.when(this.mockChatRepository.getChatByUsernames(Mockito.any()))
                    .thenReturn(Optional.of(this.chats.get(0)));
        } else {
            Mockito.when(this.mockChatRepository.getChatByUsernames(Mockito.any()))
                    .thenReturn(Optional.empty());
        }
    }

}