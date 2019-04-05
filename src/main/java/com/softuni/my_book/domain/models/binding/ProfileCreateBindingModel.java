package com.softuni.my_book.domain.models.binding;

import com.softuni.my_book.domain.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProfileCreateBindingModel {
    private int age;
    private LocalDate birthday;
    private Gender gender;
    private MultipartFile profilePicture;
    private String education;

    @Min(0)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    @NotNull
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
