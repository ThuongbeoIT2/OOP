package com.example.baeldungtest.login.service;

import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.model.VerificationToken;
import org.springframework.mail.SimpleMailMessage;

public interface LoginService {
    SimpleMailMessage constructResendVerificationTokenEmail(String contextPath, java.util.Locale locale, VerificationToken newToken, User user);

    String validatePasswordResetToken(String token);

    void changeUserPassword(User user, String password);

    VerificationToken getVerificationToken(String VerificationToken);
}
