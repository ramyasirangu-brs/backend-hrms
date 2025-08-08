package com.vagarious.backend.hrms.controller;

import com.vagarious.backend.hrms.model.LeaveRequest;
import com.vagarious.backend.hrms.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*") // allow frontend access
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveService;

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveService.getAllLeaveRequests();
    }

    @PostMapping("/apply")
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leave) {
        return leaveService.applyLeave(leave);
    }

    @PutMapping("/{id}/status")
    public LeaveRequest updateStatus(@PathVariable Long id, @RequestParam String status) {
        return leaveService.updateStatus(id, status);
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeavesByEmployee(@PathVariable String employeeId) {
        return leaveService.getLeavesByEmployee(employeeId);
    }
}
