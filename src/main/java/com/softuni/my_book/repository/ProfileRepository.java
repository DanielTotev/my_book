package com.softuni.my_book.repository;

import com.softuni.my_book.domain.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Query("SELECT p FROM Profile p JOIN p.user u WHERE u.username = :username")
    Optional<Profile> findByUserName(@Param("username") String username);
}