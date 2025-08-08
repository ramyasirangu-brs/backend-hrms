package com.vagarious.backend.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vagarious.backend.hrms.model.LeaveRequest;
import com.vagarious.backend.hrms.model.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee_EmployeeId(String employeeId);
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
