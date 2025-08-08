package com.vagarious.backend.hrms.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId; // Format: EMP001

    private String name;
    private String email;
    private String phone;
    private String designation;
    private String address;
    private String joiningDate;
    private String department;
    private String aadhar;
    private String pan;
    private String education;

    @Lob
    private byte[] resume;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experience;

    // Getters and Setters
}
