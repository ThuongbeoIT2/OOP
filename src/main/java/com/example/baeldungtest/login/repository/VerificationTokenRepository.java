package com.example.baeldungtest.login.repository;


import com.example.baeldungtest.login.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
   @Query("select o from verification o where o.token=:token")
    VerificationToken findByToken(String token);
    @Query("select o from verification o where o.user.userID=:user_ID")
    VerificationToken findByUser(int user_ID);
}
