package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Profile;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.errors.profile.InvalidProfileDataException;
import com.softuni.my_book.errors.profile.ProfileNotFoundException;
import com.softuni.my_book.repository.ProfileRepository;
import com.softuni.my_book.service.contracts.ProfileService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper mapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ModelMapper mapper, ValidationUtils validationUtils) {
        this.profileRepository = profileRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }


    @Override
    public ProfileServiceModel create(ProfileServiceModel profileServiceModel) {
        if(!this.validationUtils.isValid(profileServiceModel)) {
            throw new InvalidProfileDataException();
        }
        Profile profile = this.mapper.map(profileServiceModel, Profile.class);
        return this.mapper.map(this.profileRepository.saveAndFlush(profile), ProfileServiceModel.class);
    }

    @Override
    public ProfileServiceModel getByUsername(String username) {
        Profile profile = this.profileRepository
                .findByUserUsername(username)
                .orElseThrow(ProfileNotFoundException::new);
        return this.mapper.map(profile, ProfileServiceModel.class);
    }

    @Override
    public ProfileServiceModel getByUserId(String userId) {
        Profile profile = this.profileRepository
                .findByUserId(userId)
                .orElseThrow(ProfileNotFoundException::new);

        return this.mapper.map(profile, ProfileServiceModel.class);
    }
}
