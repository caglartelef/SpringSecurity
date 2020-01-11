package com.caglartelef.springsecurity.config;

import com.caglartelef.springsecurity.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationService {

    @Autowired
    private ApplicationProperties properties;

    /**
     * This method provide Authenticated user class.
     * */
    public ApplicationProperties.User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return properties.getUsers().get(currentUsername);
    }

    /**
     * This method provide Authenticated user name.
     * */
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
