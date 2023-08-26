package com.example.baeldungtest.login.configuration;


import com.example.baeldungtest.login.model.User;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationCompleteEvent(
            User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OnRegistrationCompleteEvent(Object source, String appUrl, Locale locale, User user) {
        super(source);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    public OnRegistrationCompleteEvent(Object source, Clock clock, String appUrl, Locale locale, User user) {
        super(source, clock);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }
}