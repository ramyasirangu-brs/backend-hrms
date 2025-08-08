package com.vagarious.backend.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileResponse {
    private Long id;
    private String name;      // maps from User.fullName
    private String email;
    private String phone;
    private String role;
    private String department;
}

