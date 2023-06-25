package com.example.baeldungtest.login.configuration;

import com.example.baeldungtest.Exception.UserNotFoundException;
import com.example.baeldungtest.login.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messages;

    @Autowired
    public RestResponseEntityExceptionHandler(MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        GenericResponse bodyOfResponse = new GenericResponse(
                messages.getMessage("message.userNotFound", null, request.getLocale()), "UserNotFound");

        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<Object> handleMail(MailAuthenticationException ex, WebRequest request) {
        GenericResponse bodyOfResponse = new GenericResponse(
                messages.getMessage("message.email.config.error", null, request.getLocale()), "MailError");

        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternal(Exception ex, WebRequest request) {
        GenericResponse bodyOfResponse = new GenericResponse(
                messages.getMessage("message.error", null, request.getLocale()), "InternalError");

        return handleExceptionInternal(
                ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
