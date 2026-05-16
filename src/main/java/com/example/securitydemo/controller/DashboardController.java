package com.example.securitydemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController
{
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/welcome")
    public ResponseEntity<String> check()
    {
        return ResponseEntity.ok().body("Welcome User!!");
    }

}
