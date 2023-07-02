package com.example.baeldungtest.login.service;


import com.example.baeldungtest.login.dtos.UserDTO;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.model.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<User> findUserByEmail(String email);
    void DisableAccount(String email);
    User registerNewUserAccount(UserDTO userDto);
    User getUser(String verificationToken);
    VerificationToken getVerificationToken(String verificationToken);
    void saveRegisteredUser(User user);
    void createVerificationToken(User user, String token);

    VerificationToken generateNewVerificationToken(String existingToken);
}
