package com.vagarious.backend.hrms.repository;

import com.vagarious.backend.hrms.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee_EmployeeId(String employeeId);
}
