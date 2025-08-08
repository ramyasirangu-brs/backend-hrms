package com.vagarious.backend.hrms.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vagarious.backend.hrms.model.Employee;
import com.vagarious.backend.hrms.model.Experience;
import com.vagarious.backend.hrms.repository.EmployeeRepository;
import com.vagarious.backend.hrms.repository.ExperienceRepository;
import org.springframework.transaction.annotation.Transactional; // Changed from jakarta

@Service
@Transactional
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepo;
    
    @Autowired 
    private EmployeeRepository employeeRepo;

    public String uploadExperienceLetter(long expId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        Experience exp = experienceRepo.findById(expId)
            .orElseThrow(() -> new IllegalArgumentException("Experience not found with ID: " + expId));
        
        exp.setExperienceLetter(file.getBytes());
        experienceRepo.save(exp); // Explicit save for clarity
        return "Experience letter uploaded";
    }

    public Experience addExperience(String employeeId, Experience experience) {
        if (experience == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }

        Employee emp = employeeRepo.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));
        
        experience.setEmployee(emp);
        return experienceRepo.save(experience);
    }
}