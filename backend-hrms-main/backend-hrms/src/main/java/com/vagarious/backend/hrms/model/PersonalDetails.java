package com.vagarious.backend.hrms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PersonalDetails {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dob;
    private String gender;
    private String maritalStatus;
    private String nationality;
}
