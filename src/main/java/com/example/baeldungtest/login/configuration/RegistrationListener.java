package com.example.baeldungtest.login.configuration;

import HaNoi.QA.libPersonal.EmailMix;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.repository.VerificationTokenRepository;
import com.example.baeldungtest.login.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token= verificationTokenRepository.findByUser(user.getUserID()).getToken();
        System.out.println(token);
        String recipientAddress = user.getEmail();
        String subject = "Xác nhận tài khoản";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String message = "Tài khoản được khởi tạo từ admin. Tên tài khoản email : " + event.getUser().getEmail() + " .Nhấp vào liên kết sau để xác nhận đăng ký tài khoản:\n" + confirmationUrl;

        EmailMix e = new EmailMix("thuong0205966@huce.edu.vn", "ztdzxxoqvmbvsfuk",0);

        e.sendContentToVer2(recipientAddress,subject,message);


    }
}