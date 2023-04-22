package com.example.jwtdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

//    @PreAuthorize("hasRole('DEV')")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from HomeController!";
    }
}
