package com.vagarious.backend.hrms.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.vagarious.backend.hrms.model.Employee;
import com.vagarious.backend.hrms.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    public Employee save(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            long count = employeeRepo.count() + 1;
            employee.setEmployeeId(String.format("EMP%03d", count));
        }
        return employeeRepo.save(employee);
    }

    public Employee getById(String id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    @Transactional
    public String uploadResume(String id, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }
        Employee emp = getById(id);
        emp.setResume(file.getBytes());
        return "Resume uploaded";  // Save is implicit with @Transactional
    }
}