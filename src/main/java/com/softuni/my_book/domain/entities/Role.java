package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role  extends BaseEntity implements GrantedAuthority {
    private String authority;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
