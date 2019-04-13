package com.softuni.my_book.integration.services;

import com.softuni.my_book.domain.entities.Profile;
import com.softuni.my_book.domain.enums.Gender;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.profile.InvalidProfileDataException;
import com.softuni.my_book.repository.ProfileRepository;
import com.softuni.my_book.service.ProfileServiceImpl;
import com.softuni.my_book.service.contracts.ProfileService;
import com.softuni.my_book.util.contracts.ValidationUtils;
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

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProfileServiceTests {

    @MockBean
    private ProfileRepository mockProfileRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUtils validationUtils;

    @MockBean
    private ProfileService profileService;

    private UserServiceModel userServiceModel;

    @Before
    public void init() {
        this.userServiceModel = new UserServiceModel();
        this.userServiceModel.setEmail("mail");
        this.userServiceModel.setUsername("User 1");
        this.userServiceModel.setId("1");

        Mockito.when(this.mockProfileRepository.saveAndFlush(Mockito.any()))
                .thenAnswer((Answer<Profile>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (Profile) args[0];
                });

        this.profileService = new ProfileServiceImpl(this.mockProfileRepository, this.mapper, this.validationUtils);
    }

    @Test
    public void profileCreate_withCorrectData_expectCorrectResult() {
        ProfileServiceModel profileServiceModel = new ProfileServiceModel();
        profileServiceModel.setUser(this.userServiceModel);
        profileServiceModel.setProfilePicture("imageUrl");
        profileServiceModel.setAge(19);
        profileServiceModel.setBirthday(LocalDate.now());
        profileServiceModel.setGender(Gender.Male);
        profileServiceModel.setEducation("Software engineer");

        ProfileServiceModel saved = this.profileService.create(profileServiceModel);

        Assert.assertNotNull(saved);
        Assert.assertEquals(profileServiceModel.getAge(), saved.getAge());
        Assert.assertEquals(profileServiceModel.getBirthday(), saved.getBirthday());
        Assert.assertEquals(profileServiceModel.getProfilePicture(), saved.getProfilePicture());
    }

    @Test(expected = InvalidProfileDataException.class)
    public void profileCreate_withIncorrectData_expectException() {
        this.profileService.create(new ProfileServiceModel());
    }
}
