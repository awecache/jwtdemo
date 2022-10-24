package com.example.jwtdemo.repository;

import com.example.jwtdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserByUsername(String username);
}
