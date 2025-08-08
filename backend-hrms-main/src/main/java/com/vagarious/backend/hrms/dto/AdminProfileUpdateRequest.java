package com.vagarious.backend.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileUpdateRequest {
    private String name;
    private String phone;
    private String department;
    // DO NOT include email or password here unless you implement change-email/change-password flows.
}
