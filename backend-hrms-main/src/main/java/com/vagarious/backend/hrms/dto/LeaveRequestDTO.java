package com.vagarious.backend.hrms.dto;

import lombok.Data;

@Data
public class LeaveRequestDTO {
    private Long id;
    private String employeeId;
    private String name;
    private String from;
    private String to;
    private String reason;
    private String date;
    private String status;

    // Constructor to convert from LeaveRequest entity
    public LeaveRequestDTO(com.vagarious.backend.hrms.model.LeaveRequest leave) {
        this.id = leave.getId();
        this.employeeId = leave.getEmployee().getEmployeeId();
        this.name = leave.getEmployee().getName();
        this.from = leave.getFromDate().toString();
        this.to = leave.getToDate().toString();
        this.reason = leave.getReason();
        this.date = leave.getRequestDate().toString();
        // Convert enum to capitalized string e.g. "Approved"
        this.status = capitalize(leave.getStatus().name());
    }

    private String capitalize(String s) {
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    // Getters and setters omitted for brevity
}
