package com.travel.tour_agency_backend.controller;

import com.travel.tour_agency_backend.entity.User;
import com.travel.tour_agency_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login() {
        // Этот эндпоинт обрабатывается Spring Security автоматически
        return "Login successful";
    }
}