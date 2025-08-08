package com.vagarious.backend.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vagarious.backend.hrms.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {}
