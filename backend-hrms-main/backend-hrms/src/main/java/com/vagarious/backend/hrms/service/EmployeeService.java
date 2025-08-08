package com.vagarious.backend.hrms.service;

import com.vagarious.backend.hrms.model.Employee;
import com.vagarious.backend.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            int count = employeeRepository.findAll().size() + 1;
            String generatedId = String.format("EMP%03d", count);
            employee.setEmployeeId(generatedId);
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void delete(String id) {
        employeeRepository.deleteById(id);
    }
}
