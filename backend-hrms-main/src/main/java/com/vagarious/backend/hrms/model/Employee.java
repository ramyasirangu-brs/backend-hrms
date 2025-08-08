package com.vagarious.backend.hrms.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Employee {
    @Id
    private String employeeId;

    private String name;
    private String email;
    private String phone;
    private String department;
    private String address;
    private String joiningDate;
    private String emergency;
    private boolean isActive;

    @Lob
    private byte[] resume;

    @Embedded
    private BankDetails bankDetails;

    @Embedded
    private PersonalDetails personalDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Experience> experienceDetails = new ArrayList<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequests = new ArrayList<>();

}
