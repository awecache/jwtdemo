package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.RoleEntity;
import com.example.jwtdemo.entity.UserEntity;
import com.example.jwtdemo.model.RoleDto;
import com.example.jwtdemo.model.UserDto;
import com.example.jwtdemo.repository.RoleRepository;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        BeanUtils.copyProperties(user, userDto); // shallow copy

        // set roles and authorities for UserDetails
        Set<RoleDto> roleDtoSet = user.getRoles().stream().map(roleEntity -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roleEntity.getId());
            roleDto.setRoleName(roleEntity.getRoleName());
            return roleDto;
        }).collect(Collectors.toSet());
        userDto.setRoles(roleDtoSet);

        return userDto;
    }

    public UserDto register(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity); // does not do deep copy

        // fetch every role form DB based on role id and set this role to user entity roles
        Set<RoleEntity> roleSet = userDto.getRoles().stream()
                .map(RoleDto::getId)
                .map(id -> roleRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        userEntity.setRoles(roleSet);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(savedEntity, userDto);
        Set<RoleDto> roleDtoSet = savedEntity.getRoles().stream().map(roleEntity -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roleEntity.getId());
            roleDto.setRoleName(roleEntity.getRoleName());
            return roleDto;
        }).collect(Collectors.toSet());
        userDto.setRoles(roleDtoSet);

        return userDto;
    }
}
