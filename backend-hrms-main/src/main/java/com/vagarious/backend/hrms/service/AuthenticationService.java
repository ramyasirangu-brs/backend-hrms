package com.vagarious.backend.hrms.service;

import com.vagarious.backend.hrms.dto.AdminRegisterRequest;
import com.vagarious.backend.hrms.dto.AuthRequest;
import com.vagarious.backend.hrms.dto.AuthResponse;
import com.vagarious.backend.hrms.model.User;
import com.vagarious.backend.hrms.repository.UserRepository;
import com.vagarious.backend.hrms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            String role = user != null ? user.getRole().name() : "UNKNOWN";

            return new AuthResponse(token, "Login successful", role);
        } catch (Exception e) {
            return new AuthResponse(null, "Invalid email or password", null);
        }
    }

    public AuthResponse registerAdmin(AdminRegisterRequest request) {
        // Check if admin already exists
        if (userRepository.existsByRole(User.Role.ADMIN)) {
            return new AuthResponse(null, "Admin already exists. Only one admin is allowed.", null);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists", null);
        }

        User admin = new User();
        admin.setUsername(request.getFullName()); // or use a dedicated display name if needed
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(User.Role.ADMIN);
        admin.setEnabled(true);

        userRepository.save(admin);

        // Generate token for the new admin
        UserDetails userDetails = userRepository.findByEmail(request.getEmail()).orElse(null);
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, "Admin registered successfully", User.Role.ADMIN.name());
    }
}
