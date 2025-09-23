package com.alibou.app.security;

import com.alibou.app.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditorAware implements AuditorAware<String> { //should return ID of the user

    @Override
    public Optional<String> getCurrentAuditor() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) //instanceof is a Java operator that checks if an object is an instance of a specific class or interface.
        {
            return Optional.empty();
        }
        final User user = (User) authentication.getPrincipal();
        return Optional.ofNullable(user.getId());
    }

}

//I've worked on applications where I didn't have to this, what happens if I don't use this class or rather what alternatives do I have?
// Why does AuditorAware<> have a type, What is it exactly?