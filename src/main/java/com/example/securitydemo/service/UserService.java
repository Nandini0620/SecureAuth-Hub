package com.example.securitydemo.service;

import com.example.securitydemo.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService
{

    ResponseEntity<User> registerUser(User user);

    ResponseEntity<String> loginUser(String email, String password);
}
