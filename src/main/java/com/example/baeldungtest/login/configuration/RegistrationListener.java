package com.example.baeldungtest.login.configuration;

import HaNoi.QA.libPersonal.EmailMix;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;



    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        service.createVerificationToken(user, token);


        String recipientAddress = user.getEmail();
        String subject = "Xác nhận đăng ký tài khoản";
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
        String message = "Nhấp vào liên kết sau để xác nhận đăng ký tài khoản:\n" + confirmationUrl;

        EmailMix e = new EmailMix("thuong0205966@huce.edu.vn", "ztdzxxoqvmbvsfuk",0);

        e.sendContentToVer2(recipientAddress,subject,message);


    }
}