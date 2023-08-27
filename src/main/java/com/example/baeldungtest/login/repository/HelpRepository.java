package com.example.baeldungtest.login.repository;

import com.example.baeldungtest.login.model.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelpRepository extends JpaRepository<Help,Integer> {
    @Query("select o from help o where o.email=:email")
    Optional<Help> findByEmail(String email);

}
