package com.softuni.my_book.domain.models.view;

import com.softuni.my_book.domain.entities.Role;

import java.util.Set;

public class UserAllViewModel {
    private String id;
    private String username;
    private Set<Role> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public boolean isAdmin(){
        return getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isModerator() {
        return getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_MODERATOR"));
    }

    public boolean isUser() {
        return getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_USER"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
