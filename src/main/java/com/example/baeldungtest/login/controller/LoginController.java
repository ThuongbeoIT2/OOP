package com.example.baeldungtest.login.controller;


import com.example.baeldungtest.login.configuration.OnRegistrationCompleteEvent;
import com.example.baeldungtest.login.dtos.UserDTO;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.model.VerificationToken;
import com.example.baeldungtest.login.service.IUserService;
import com.example.baeldungtest.login.service.LoginService;
import com.example.baeldungtest.login.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private IUserService service;
    @Autowired
    private MessageSource messages;

    @Autowired
    private LoginService loginService;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.service = userService;
        this.loginService = loginService;
    }

    @GetMapping(value = {"/", "/login"})
    public String login(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Optional<User> user = service.findUserByEmail(userDTO.getEmail().trim());

        if (user.isPresent()) {
            System.out.println(user.get());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //So sánh với mật khẩu mã hóa
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword().trim());
            System.out.println(userDTO.getPassword().trim());
            System.out.println(user.get().getPassword());

            if (user.get().getPassword().equals(userDTO.getPassword())) {
                if (user.get().isEnabled()) {
                    return "redirect:/home";

                } else {
                    bindingResult.rejectValue("enable", "error.user", "Account Disabled");
                    return "login";
                }

            } else {
                bindingResult.rejectValue("password", "error.user", "Invalid email or password");
                return "login";
            }

        } else {
            bindingResult.rejectValue("email", "error.user", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping(value = "/admin/registration")
    public String registration(Model model) {
        System.out.println("Registration");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = service.findUserByEmail(auth.getName());
        model.addAttribute("user", new UserDTO());
        System.out.println("Chuyển vào view");
        return "registration";
    }

    @PostMapping(value = "/admin/registration")
    public String createNewUser(@Valid UserDTO user, BindingResult bindingResult, Model model) {
        Optional<User> userExists = service.findUserByEmail(user.getEmail().trim());
        if (userExists.isPresent()) {
            bindingResult.rejectValue("email", "error.user",
                    "Email đã đăng ký với tài khoản khác. Vui lòng thử lại.");

        }
        System.out.println(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Có lỗi xảy ra. Email đã tồn tại hoặc mật khẩu không khớp.");
            return "registration";
        } else {
            User newAccount = service.registerNewUserAccount(user);

            String appUrl = "http://localhost:8086";
            JsonFormat.Value request = new JsonFormat.Value();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newAccount,
                    request.getLocale(), appUrl));

            System.out.println("new acc" + newAccount);
            model.addAttribute("successMessage", "Account Verification Required");
            model.addAttribute("user", newAccount);
            return "redirect:/home";
        }

    }

    @GetMapping("/admin/listuser")
    public String getAllUser(Model model) {
        List<User> list = service.getAllUser();
        list.forEach(p -> {
            System.out.println(p.getEmail());
        });
        model.addAttribute("listuser", list);
        return "/admin/listuser";
    }

    @GetMapping("/admin/disable/{email}")
    public String DisableAcccount(@PathVariable String email) {
        service.DisableAccount(email);
        return "redirect:/admin/listuser";
    }


    /// Đổi mật khẩu
    @GetMapping("/resetpassword")
    public String resetPassword(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = service.findUserByEmail(auth.getName());
        System.out.println(user.get());
        model.addAttribute("user", service.findUserByEmail(user.get().getEmail()));
        return "resetpassword";
    }

    @PostMapping("/confirmreset")
    public String confirmReset(@RequestParam(name = "oldpassword", required = true) String oldPassword,
                               @RequestParam(name = "password", required = true) String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = service.findUserByEmail(auth.getName());
        System.out.println(user.get());
        User existingUser = service.findUserByEmail(user.get().getEmail()).orElse(null);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //So sánh với mật khẩu mã hóa
        String oldEncodedPassword = passwordEncoder.encode(oldPassword);

        if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {   //Dùng equal là cút
            //So sánh với mật khẩu mã hóa
            String newEncodedPassword = passwordEncoder.encode(password);
            existingUser.setPassword(newEncodedPassword);
            service.saveRegisteredUser(existingUser);
            return "redirect:/home";
        } else {
            return "redirect:/resetpassword";
        }
    }


    @GetMapping(value = "/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = service.findUserByEmail(auth.getName());
        System.out.println(user.get().getPassword());
        model.addAttribute("userName", "Welcome " + user.get().getEmail());
        model.addAttribute("userFullName", user.get().getFirstname() + " " + user.get().getLastname());
        if (user.get().getRoles().size() == 2) {
            return "/admin/home";
        } else {
            return "/admin/homeuser";
        }

    }

    // Để sau
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(
            Locale locale, Model model, @RequestParam("token") String token) {
        System.out.println("Bắt đầu xác thực tài khoản");
        System.out.println(token);
        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true); // Biến kích hoạt trang thái hoạt động
        service.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }
}
//    @GetMapping("/user/resendRegistrationToken")
//    public GenericResponse resendRegistrationToken(
//            HttpServletRequest request, @RequestParam("token") String existingToken) {
//        VerificationToken newToken = service.generateNewVerificationToken(existingToken);
//
//        User user = service.getUser(newToken.getToken());
//        String appUrl =
//                "http://" + request.getServerName() +
//                        ":" + request.getServerPort() +
//                        request.getContextPath();
//        SimpleMailMessage email =
//                constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
//        mailSender.send(email);
//
//        return new GenericResponse(
//                messages.getMessage("message.resendToken", null, request.getLocale()));
//    }
//
//    private SimpleMailMessage constructResendVerificationTokenEmail(String appUrl, Locale locale, VerificationToken newToken, User user) {
//        String confirmationUrl = appUrl + "/registrationConfirm?token=" + newToken.getToken();
//        String message = messages.getMessage("auth.email.resendVerificationToken.message", null, locale);
//        String subject = messages.getMessage("auth.email.resendVerificationToken.subject", null, locale);
//
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(user.getEmail());
//        email.setSubject(subject);
//        email.setText(message + " " + confirmationUrl);
//        email.setFrom("hoangtuananh1772003@gmail.com"); // Set the sender's email address
//
//        return email;
//    }