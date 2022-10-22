package com.example.jwtdemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomUserDetailService implements UserDetailsService {

    // validate user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("John")) {
            return new User("John", "secret", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }
}
