package com.vagarious.backend.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vagarious.backend.hrms.model.LeaveRequest;
import com.vagarious.backend.hrms.model.LeaveStatus;
import com.vagarious.backend.hrms.repository.LeaveRequestRepository;
import com.vagarious.backend.hrms.service.LeaveRequestService;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRepo;

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepo.findAll();
    }

    @Override
    public LeaveRequest applyLeave(LeaveRequest leave) {
        return leaveRepo.save(leave);
    }

    @Override
    public LeaveRequest updateStatus(Long id, String status) {
        LeaveRequest leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.valueOf(status.toUpperCase()));
        return leaveRepo.save(leave);
    }

    @Override
    public List<LeaveRequest> getLeavesByEmployee(String employeeId) {
        return leaveRepo.findByEmployee_EmployeeId(employeeId);
    }
    @Override
    public List<LeaveRequest> getLeavesByStatus(String status) {
        LeaveStatus leaveStatus;
        try {
            leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid leave status: " + status);
        }
        return leaveRepo.findByStatus(leaveStatus);
    }
}

