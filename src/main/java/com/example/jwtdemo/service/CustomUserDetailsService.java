package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.UserEntity;
import com.example.jwtdemo.model.UserDto;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // validate user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist!");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);

        return userDto;
    }

    public UserDto register(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(savedEntity, userDto);
        return userDto;
    }
}
