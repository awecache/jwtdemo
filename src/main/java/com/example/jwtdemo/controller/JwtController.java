package com.example.jwtdemo.controller;

import com.example.jwtdemo.model.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class JwtController {

    @PostMapping("/generateToken")
    public ResponseEntity<> generateToken(@RequestBody JwtRequest request) {

    }
}
