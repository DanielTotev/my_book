package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.ProfileServiceModel;

public interface ProfileService {
    ProfileServiceModel create(ProfileServiceModel profileServiceModel);

    ProfileServiceModel getByUsername(String username);

    ProfileServiceModel getByUserId(String userId);
}