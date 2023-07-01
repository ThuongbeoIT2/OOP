package com.example.baeldungtest.login.service;

import com.example.baeldungtest.Exception.UserAlreadyExistException;
import com.example.baeldungtest.login.dtos.UserDTO;
import com.example.baeldungtest.login.model.Role;
import com.example.baeldungtest.login.model.User;
import com.example.baeldungtest.login.model.VerificationToken;
import com.example.baeldungtest.login.repository.RoleRepository;
import com.example.baeldungtest.login.repository.UserRepository;
import com.example.baeldungtest.login.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void DisableAccount(String email) {
        User userfound= userRepository.findByEmail(email.trim()).orElse(null);
        if(userfound!= null){
            userfound.setEnabled(false);
            userRepository.save(userfound);
        }
    }

    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
        }

        User user = new User();
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        Optional<Role> userRole = roleRepository.findById(2);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole.get());
        user.setRoles(roles);
        System.out.println(userRole);
        userRepository.save(user);
//        String token = UUID.randomUUID().toString();
//        System.out.println(token);
//        createVerificationToken(user, token);
        return user;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken oldToken = tokenRepository.findByToken(existingToken);
        if (oldToken == null) {
            // Existing token not found
            return null;
        }

        // Generate a new token
        String newTokenString = UUID.randomUUID().toString();
        VerificationToken newToken = new VerificationToken(newTokenString, oldToken.getUser());
        newToken.setExpiryDate(calculateExpiryDate(VerificationToken.EXPIRATION)); // Set the expiry date

        // Save the new token
        tokenRepository.save(newToken);

        // Delete the old token
        tokenRepository.delete(oldToken);

        return newToken;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return calendar.getTime();
    }

}
