package com.example.jwtdemo.controller;

import com.example.jwtdemo.entity.UserEntity;
import com.example.jwtdemo.model.JwtRequest;
import com.example.jwtdemo.model.JwtResponse;
import com.example.jwtdemo.model.UserDto;
import com.example.jwtdemo.service.CustomUserDetailsService;
import com.example.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api")
@CrossOrigin
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto userEntity = customUserDetailsService.register(userDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest request) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public UserDto getCurrentUser(Principal principal) {
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(principal.getName());
        return (UserDto) userDetails;
    }
}
