package com.vagarious.backend.hrms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vagarious.backend.hrms.dto.AdminProfileResponse;
import com.vagarious.backend.hrms.dto.AdminProfileUpdateRequest;
import com.vagarious.backend.hrms.model.User;
import com.vagarious.backend.hrms.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // ------------------- EMPLOYEE METHODS -------------------

    public User createEmployee(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User employee = new User();
        employee.setUsername(username);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setRole(User.Role.EMPLOYEE);
        employee.setEnabled(true);

        return userRepository.save(employee);
    }

    public List<User> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.EMPLOYEE)
                .toList();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteEmployee(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getRole() == User.Role.EMPLOYEE) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // ------------------- ADMIN PROFILE METHODS -------------------

    // Map User entity to DTO
    private AdminProfileResponse toAdminProfile(User user) {
        return new AdminProfileResponse(
                user.getId(),
                user.getFullName(), // maps to "name" in DTO
                user.getEmail(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getDepartment()
        );
    }

    // Get profile by email (email comes from Authentication.getName())
    public AdminProfileResponse getProfileByEmail(String email) {
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return toAdminProfile(admin);
    }

    // Update profile by email
    public AdminProfileResponse updateProfileByEmail(String email, AdminProfileUpdateRequest request) {
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (request.getName() != null) admin.setFullName(request.getName());
        if (request.getPhone() != null) admin.setPhone(request.getPhone());
        if (request.getDepartment() != null) admin.setDepartment(request.getDepartment());

        User updated = userRepository.save(admin);
        return toAdminProfile(updated);
    }
    public boolean changeAdminPassword(String loginIdentifier, String currentPassword, String newPassword) {
        // Try finding by username first, then by email
        Optional<User> userOpt = userRepository.findByUsername(loginIdentifier);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(loginIdentifier);
        }

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }


    
    
}
