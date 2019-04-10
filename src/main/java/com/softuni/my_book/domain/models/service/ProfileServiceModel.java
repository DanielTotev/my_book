package com.softuni.my_book.domain.models.service;

import com.softuni.my_book.domain.enums.Gender;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProfileServiceModel {
    private int age;
    private LocalDate birthday;
    private Gender gender;
    private String profilePicture;
    private String education;
    private UserServiceModel user;

    public ProfileServiceModel() {
    }

    @Min(0)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @NotNull
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @NotNull
    @NotEmpty
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @NotNull
    @NotEmpty
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
