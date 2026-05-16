package com.example.securitydemo.serviceImpl;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService
{
    private UserRepository userRepository;

    public UserLoginServiceImpl() {
    }

    @Autowired
    public UserLoginServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user=userRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not Found" + email)
        );
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();

    }
}
