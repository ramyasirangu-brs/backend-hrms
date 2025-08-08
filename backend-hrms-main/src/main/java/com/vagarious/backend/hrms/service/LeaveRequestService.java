package com.vagarious.backend.hrms.service;

import com.vagarious.backend.hrms.model.LeaveRequest;
import java.util.List;

public interface LeaveRequestService {
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getLeavesByStatus(String status);
    LeaveRequest applyLeave(LeaveRequest leave);
    LeaveRequest updateStatus(Long id, String status);
    List<LeaveRequest> getLeavesByEmployee(String employeeId);
}

