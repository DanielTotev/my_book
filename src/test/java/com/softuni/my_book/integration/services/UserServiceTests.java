package com.softuni.my_book.integration.services;


import com.softuni.my_book.domain.entities.Role;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.user.IllegalUserDataException;
import com.softuni.my_book.errors.user.UserAlreadyExistsException;
import com.softuni.my_book.errors.user.UserNotFoundException;
import com.softuni.my_book.repository.RoleRepository;
import com.softuni.my_book.repository.UserRepository;
import com.softuni.my_book.service.UserServiceImpl;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @MockBean
    private UserRepository mockUserRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ValidationUtils validationUtils;

    private UserService userService;

    @Before
    public void beforeEach() {
        this.userService = new UserServiceImpl(mockUserRepository, mapper, encoder, roleRepository, validationUtils);
    }


    @Test
    public void findById_withCorrectParam_expectToReturnCorrect() {
        String userId = "123";
        User user = new User();
        user.setUsername("Daniel");
        user.setId(userId);
        Mockito.when(this.mockUserRepository.findById(userId))
                .thenReturn(Optional.of(user));
        UserServiceModel result = this.userService.findById(userId);
        Assert.assertEquals(userId, result.getId());
        Assert.assertEquals(user.getUsername(), result.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void findById_withIncorrectData_expectToThrowException() {
        String id = "123";
        Mockito.when(this.mockUserRepository.findById(id))
                .thenReturn(Optional.empty());
        this.userService.findById(id);
    }

    @Test
    public void findByUsername_withCorrectData_expectCorrectResult() {
        String username = "Test user";
        User user = new User();
        user.setUsername(username);
        Mockito.when(this.mockUserRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        UserServiceModel result = userService.findByUsername(username);
        Assert.assertEquals(username, result.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void findByUsername_withIncorrectData_expectToThrowException() {
        String username = "Test user";
        Mockito.when(this.mockUserRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        this.userService.findByUsername(username);
    }

    @Test
    public void findAll_withTwoUsers_expectTwoUsers() {
        int expectedSize = 2;
        Mockito.when(this.mockUserRepository.findAll())
                .thenReturn(List.of(new User(), new User()));
        List<UserServiceModel> users = this.userService.findAll();
        Assert.assertEquals(expectedSize, users.size());
    }

    @Test
    public void findAll_withNoUsers_expectEmptyList() {
        int expectedSize = 0;
        Mockito.when(this.mockUserRepository.findAll())
            .thenReturn(new ArrayList<>());

        List<UserServiceModel> users = this.userService.findAll();
        Assert.assertEquals(expectedSize, users.size());
    }

    @Test
    public void registerUser_withCorrectData_whenNoUsersInDb_expectCorrectResult() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("User 1 username");
        userServiceModel.setPassword("Password");
        userServiceModel.setEmail("email");

        Mockito.when(this.roleRepository.saveAndFlush(Mockito.any()))
                .thenReturn(new Role());
        Mockito.when(this.roleRepository.findByAuthority("ROLE_ADMIN"))
                .thenReturn(Optional.of(role));
        Mockito.when(this.mockUserRepository.count())
                .thenReturn(0L);
        Mockito.when(this.mockUserRepository.findByUsername(userServiceModel.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.when(this.mockUserRepository.saveAndFlush(Mockito.any()))
                .thenAnswer(new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        return (User) args[0];
                    }
                });


        UserServiceModel saved = this.userService.registerUser(userServiceModel);

        Assert.assertEquals(userServiceModel.getUsername(), saved.getUsername());
        Assert.assertEquals(userServiceModel.getEmail(), saved.getEmail());
        Assert.assertNotEquals(userServiceModel.getPassword(), saved.getPassword());
        Assert.assertTrue(saved.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    public void registerUser_withCorrectData_expectCorrectResult() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("User 1 username");
        userServiceModel.setPassword("Password");
        userServiceModel.setEmail("email");

        Mockito.when(this.roleRepository.saveAndFlush(Mockito.any()))
                .thenReturn(new Role());
        Mockito.when(this.roleRepository.findByAuthority("ROLE_USER"))
                .thenReturn(Optional.of(role));
        Mockito.when(this.mockUserRepository.count())
                .thenReturn(1L);
        Mockito.when(this.mockUserRepository.findByUsername(userServiceModel.getUsername()))
                .thenReturn(Optional.empty());
        Mockito.when(this.mockUserRepository.saveAndFlush(Mockito.any()))
                .thenAnswer(new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        return (User) args[0];
                    }
                });


        UserServiceModel saved = this.userService.registerUser(userServiceModel);

        Assert.assertEquals(userServiceModel.getUsername(), saved.getUsername());
        Assert.assertEquals(userServiceModel.getEmail(), saved.getEmail());
        Assert.assertNotEquals(userServiceModel.getPassword(), saved.getPassword());
        Assert.assertTrue(saved.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER")));
    }

    @Test(expected = IllegalUserDataException.class)
    public void register_withIncorrectData_expectException() {
        UserServiceModel user = new UserServiceModel();
        this.userService.registerUser(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void register_withExistingUser_expectException() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("User 1 username");
        userServiceModel.setPassword("Password");
        userServiceModel.setEmail("email");

        Mockito.when(this.mockUserRepository.findByUsername(userServiceModel.getUsername()))
                .thenReturn(Optional.of(new User()));

        this.userService.registerUser(userServiceModel);
    }

}