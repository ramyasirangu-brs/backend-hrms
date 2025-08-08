package com.vagarious.backend.hrms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vagarious.backend.hrms.dto.LeaveRequestDTO;
import com.vagarious.backend.hrms.model.LeaveRequest;
import com.vagarious.backend.hrms.service.LeaveRequestService;



@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveService;

    // Get all leaves or filter by status
    
    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests(@RequestParam(required = false) String status) {
        List<LeaveRequest> leaves = (status == null) ?
            leaveService.getAllLeaveRequests() :
            leaveService.getLeavesByStatus(status);

        List<LeaveRequestDTO> dtoList = leaves.stream()
            .map(LeaveRequestDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    
   

    @PostMapping("/apply")
    public ResponseEntity<LeaveRequestDTO> applyLeave(@RequestBody com.vagarious.backend.hrms.model.LeaveRequest leave) {
        com.vagarious.backend.hrms.model.LeaveRequest saved = leaveService.applyLeave(leave);
        return ResponseEntity.ok(new LeaveRequestDTO(saved));
    }
    
    
    
    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequestDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        com.vagarious.backend.hrms.model.LeaveRequest updated = leaveService.updateStatus(id, status);
        return ResponseEntity.ok(new LeaveRequestDTO(updated));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeavesByEmployee(@PathVariable String employeeId) {
        List<com.vagarious.backend.hrms.model.LeaveRequest> leaves = leaveService.getLeavesByEmployee(employeeId);
        List<LeaveRequestDTO> dtoList = leaves.stream()
                .map(LeaveRequestDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // New endpoints for admin approve/reject
    @PostMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestDTO> approveLeave(@PathVariable Long id) {
        LeaveRequest updated = leaveService.updateStatus(id, "APPROVED");
        return ResponseEntity.ok(new LeaveRequestDTO(updated));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<LeaveRequestDTO> rejectLeave(@PathVariable Long id) {
        LeaveRequest updated = leaveService.updateStatus(id, "REJECTED");
        return ResponseEntity.ok(new LeaveRequestDTO(updated));
    }
}
