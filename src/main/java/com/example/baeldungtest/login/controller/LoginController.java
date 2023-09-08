package com.example.baeldungtest.login.controller;


import HaNoi.QA.libPersonal.EmailMix;
import com.example.baeldungtest.login.configuration.OnRegistrationCompleteEvent;
import com.example.baeldungtest.login.dtos.UserDTO;
import com.example.baeldungtest.login.model.Help;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.model.VerificationToken;
import com.example.baeldungtest.login.repository.HelpRepository;
import com.example.baeldungtest.login.repository.VerificationTokenRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.service = userService;
        this.loginService = loginService;
    }

    @GetMapping(value = {"/", "/login","/login.html"})
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

    @PostMapping(value = "/resettoken")
    public String resetToken(@RequestParam(name = "email", required = true) String email,
                             @RequestParam(name = "title", required = true) String title){
        Optional<User> RS= service.findUserByEmail(email.trim());
        if(RS.isPresent()){
            Help help= new Help();
            help.setUser(RS.get());
            help.setTitle(title);
            help.setEmail(email);
            helpRepository.save(help);
            System.out.println("Tạo yêu cầu thành công cho email:"+ email);
            return "/successRegister.html";
        }
        return "redirect:/resendRegistrationToken";
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
    @GetMapping("/registrationConfirm.html")
    public String confirmRegistration(
            Locale locale, Model model, HttpServletRequest req, @RequestParam("token") String token) {
        System.out.println(req.getRequestURI());
        System.out.println("Start account verification");
        System.out.println(token);

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            System.out.println("Không tồn tại token");
            String message = "Không tồn tại Token";
            model.addAttribute("message", message);
            return "redirect:/login.html";
        }
        System.out.println("Pass if-1");
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() <= cal.getTime().getTime()) {
            System.out.println("Token quá hạn");
            model.addAttribute("message","Token đã quá hạn");
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/login.html";
        }
        System.out.println("Pass if-2");
        user.setEnabled(true); // Activate user's account
        service.saveRegisteredUser(user);
        System.out.println("Kích hoạt thành công");

        return "redirect:/login.html";
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
    @GetMapping("/resendRegistrationToken")
    public String resendRegistrationToken(Model model) {
       model.addAttribute("help",new Help());
       return "resendtoken";
    }
    @GetMapping(value = "/admin/help")
    public String GetAllHelp(Model model){
        model.addAttribute("helplist",helpRepository.findAll());
        return "/admin/listhelp";
    }
    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model) {
        model.addAttribute("help",new Help());
        return "/forgotpassword";
    }
    @PostMapping("/resendPassword")
    public String SendPassWord(@RequestParam(name = "email", required = true) String email,
                               @RequestParam(name = "title", required = true) String title){
        Optional<User> RS= service.findUserByEmail(email.trim());
        if(RS.isPresent()){
            Help help= new Help();
            help.setUser(RS.get());
            help.setTitle(title);
            help.setEmail(email);
            helpRepository.save(help);
            System.out.println("Tạo yêu cầu thành công cho email:"+ email);
            return "successRegister.html";
        }
       return "error.html";
    }
    @GetMapping(value = "/admin/resendtoken/{email}")
    public String ResendToken(@PathVariable String email){
        Optional<User> user = service.findUserByEmail(email.trim());
        Optional<Help> help = helpRepository.findByEmail(email);
        if (user.isPresent() && help.isPresent()){
            VerificationToken verificationToken= verificationTokenRepository.findByUser(user.get().getUserID());
            service.generateNewVerificationToken(verificationToken.getToken());
            String appUrl = "http://localhost:8086";
            JsonFormat.Value request = new JsonFormat.Value();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user.get(),
                    request.getLocale(), appUrl));
            System.out.println("Thành công");
            helpRepository.delete(help.get());
            return "redirect:/home";
        }
        return "error.html";
    }
    @GetMapping(value = "/admin/resendpassword/{email}")
    public String ResendPassword(@PathVariable String email){
        Optional<User> user = service.findUserByEmail(email.trim());
        Optional<Help> help = helpRepository.findByEmail(email);
        if (user.isPresent() && help.isPresent()){
            String recipientAddress = user.get().getEmail();
            String subject = "Cấp lại mật khẩu";
            String newpassword = UUID.randomUUID().toString();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.get().setPassword(passwordEncoder.encode(newpassword));
            String message = "Tài khoản được cấp lại từ admin. Tên tài khoản email : " + user.get().getEmail() +" Mật khẩu mặc định :"+  newpassword+". Vui lòng đổi mật khẩu ngay sau khi đăng nhập";
            EmailMix e = new EmailMix("thuong0205966@huce.edu.vn", "ztdzxxoqvmbvsfuk",0);
            e.sendContentToVer2(recipientAddress,subject,message);
            System.out.println("Thành công");
            helpRepository.delete(help.get());
            return "redirect:/home";
        }
        return "error.html";
    }

}

