package com.vagarious.backend.hrms.controller;

import com.vagarious.backend.hrms.model.Employee;
import com.vagarious.backend.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        employee.setEmployeeId(id); // preserve ID
        return employeeService.save(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        employeeService.delete(id);
    }

    @PatchMapping("/{id}/deactivate")
    public Employee deactivate(@PathVariable String id) {
        Employee e = employeeService.getById(id);
        e.setIsActive(false);
        return employeeService.save(e);
    }

    @PatchMapping("/{id}/activate")
    public Employee activate(@PathVariable String id) {
        Employee e = employeeService.getById(id);
        e.setIsActive(true);
        return employeeService.save(e);
    }
}
