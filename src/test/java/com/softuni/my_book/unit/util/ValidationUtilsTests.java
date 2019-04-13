package com.softuni.my_book.unit.util;


import com.softuni.my_book.domain.enums.Gender;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.util.ValidationUtilImpl;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ValidationUtilsTests {
    private ValidationUtils validationUtils;

    @Before
    public void setUp() {
        this.validationUtils = new ValidationUtilImpl();
    }

    @Test
    public void isValid_withValidData_shouldReturnTrue() {
        ProfileServiceModel profileServiceModel = new ProfileServiceModel();
        profileServiceModel.setUser(new UserServiceModel());
        profileServiceModel.setProfilePicture("imageUrl");
        profileServiceModel.setAge(19);
        profileServiceModel.setBirthday(LocalDate.now());
        profileServiceModel.setGender(Gender.Male);
        profileServiceModel.setEducation("Software engineer");

        boolean isValid = this.validationUtils.isValid(profileServiceModel);

        Assert.assertTrue(isValid);
    }

    @Test
    public void isValid_withInvalidData_shouldReturnFalse() {
        ProfileServiceModel profileServiceModel = new ProfileServiceModel();
        profileServiceModel.setUser(new UserServiceModel());
        profileServiceModel.setProfilePicture("imageUrl");
        profileServiceModel.setAge(-1);
        profileServiceModel.setBirthday(LocalDate.now());
        profileServiceModel.setGender(Gender.Male);
        profileServiceModel.setEducation("Software engineer");

        boolean isValid = this.validationUtils.isValid(profileServiceModel);
        Assert.assertFalse(isValid);
    }

}
