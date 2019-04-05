package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Profile;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.repository.ProfileRepository;
import com.softuni.my_book.service.contracts.ProfileService;
import com.softuni.my_book.service.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ModelMapper mapper, UserService userService) {
        this.profileRepository = profileRepository;
        this.mapper = mapper;
        this.userService = userService;
    }


    @Override
    public ProfileServiceModel create(ProfileServiceModel profileServiceModel) {
        Profile profile = this.mapper.map(profileServiceModel, Profile.class);

        try {
            return this.mapper.map(this.profileRepository.saveAndFlush(profile), ProfileServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProfileServiceModel getByUsername(String username) {
//        return this.profileRepository
//                .findAll()
//                .stream()
//                .filter(x -> x.getUser().getUsername().equals(username))
//                .map(x -> this.mapper.map(x, ProfileServiceModel.class))
//                .findFirst()
//                .orElse(null);

        Profile profile = this.profileRepository.findByUserName(username).orElse(null);

        if(profile == null) {
            return null;
        }

        return this.mapper.map(profile, ProfileServiceModel.class);
    }
}
