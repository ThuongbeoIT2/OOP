package com.example.baeldungtest.login.repository;

import com.example.baeldungtest.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select o from users o where o.email=:email")
    Optional<User> findByEmail(String email);

}