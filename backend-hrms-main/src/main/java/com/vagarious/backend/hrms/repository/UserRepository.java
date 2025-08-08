package com.vagarious.backend.hrms.repository;

import com.vagarious.backend.hrms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // OLD methods (can keep if still needed)
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // NEW methods for email-based login
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByRole(User.Role role);
}
