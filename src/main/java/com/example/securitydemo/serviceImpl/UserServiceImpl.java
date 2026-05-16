package com.example.securitydemo.serviceImpl;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.security.JwtUtil;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
    }


    @Override
    public ResponseEntity<User> registerUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @Override
    public ResponseEntity<String> loginUser(String email, String password)
    {
        Authentication authentication;

        try
        {
            authentication=authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(email,password));
        }
        catch(Exception e)
        {
            throw e;
        }

        if(authentication.isAuthenticated())
        {
            String token = jwtUtil.generateToken(email);
            String result = jwtUtil.extractToken(token);
            return ResponseEntity.ok().body("Login Successfully! \n" + token );
        }

        return ResponseEntity.ok().body("Invalid credentials");
    }
}
