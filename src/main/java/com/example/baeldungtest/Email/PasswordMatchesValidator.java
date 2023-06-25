package com.example.baeldungtest.Email;

import com.example.baeldungtest.login.dtos.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO userDto, ConstraintValidatorContext context) {
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }
}
