package com.example.baeldungtest.login.configuration;

import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final IUserService userService;
    private final MessageSource messages;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(IUserService userService, MessageSource messages, JavaMailSender mailSender) {
        this.userService = userService;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Xác nhận đăng ký tài khoản";
        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
        String message = "Nhấp vào liên kết sau để xác nhận đăng ký tài khoản:\n" + confirmationUrl;

        // Configure TLS properties
        Security.setProperty(

                "jdk.tls.client.protocols", "TLSv1.2");
        Security.setProperty(

                "jdk.tls.disabledAlgorithms", "");
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
