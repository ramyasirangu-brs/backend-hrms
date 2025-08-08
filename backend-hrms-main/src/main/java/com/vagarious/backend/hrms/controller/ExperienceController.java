package com.vagarious.backend.hrms.controller;

import com.vagarious.backend.hrms.model.Experience;
import com.vagarious.backend.hrms.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/{empId}/add")
    public Experience addExperience(@PathVariable String empId, @RequestBody Experience exp) {
        return experienceService.addExperience(empId, exp);
    }

    @PostMapping("/{expId}/uploadLetter")
    public String uploadLetter(@PathVariable Long expId, @RequestParam MultipartFile file) throws IOException {
        return experienceService.uploadExperienceLetter(expId, file);
    }
}