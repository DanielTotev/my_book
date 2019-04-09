package com.softuni.my_book.service;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.domain.entities.Role;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.IllegalUserDataException;
import com.softuni.my_book.errors.UserAlreadyExistsException;
import com.softuni.my_book.errors.UserNotFoundException;
import com.softuni.my_book.repository.RoleRepository;
import com.softuni.my_book.repository.UserRepository;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final ValidationUtils validationUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, BCryptPasswordEncoder encoder, RoleRepository roleRepository, ValidationUtils validationUtils) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRoles();
        if (!this.validationUtils.isValid(userServiceModel)) {
            throw new IllegalUserDataException();
        }

        if (this.userRepository.findByUsername(userServiceModel.getUsername()).orElse(null) != null) {
            throw new UserAlreadyExistsException();
        }

        User user = this.mapper.map(userServiceModel, User.class);
        user.setPassword(this.encoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN").orElse(null));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER").orElse(null));
        }

        this.userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public UserServiceModel findById(String id) {
        return null;
    }

    @Override
    public UserServiceModel merge(UserServiceModel userServiceModel) {
//        User user = this.mapper.map(userServiceModel, User.class);

        return null;
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(x -> this.mapper.map(x, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean makeFriends(String firstUserId, String secondUserId) {
        User firstUser = this.userRepository.findById(firstUserId).orElse(null);
        User secondUser = this.userRepository.findById(secondUserId).orElse(null);

        if (firstUser == null || secondUser == null) {
            throw new UserNotFoundException();
        }

        firstUser.getFriends().add(secondUser);
        secondUser.getFriends().add(firstUser);

        this.userRepository.saveAndFlush(firstUser);
        this.userRepository.saveAndFlush(secondUser);
        return true;
    }

    @Override
    public List<UserServiceModel> findAllWithDifferentUsername(String username) {
        return this.userRepository.findAllByUsernameNot(username)
                .stream()
                .map(u -> this.mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND_MESSAGE));
    }

    private void seedRoles() {
        if (this.roleRepository.count() == 0) {
            Role user = new Role();
            user.setAuthority("ROLE_USER");

            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");

            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");

            this.roleRepository.saveAndFlush(user);
            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
        }
    }
}
