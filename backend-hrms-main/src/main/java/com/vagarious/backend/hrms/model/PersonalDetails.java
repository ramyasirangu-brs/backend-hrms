package com.vagarious.backend.hrms.model;

import jakarta.persistence.*;

@Embeddable
public class PersonalDetails {
    private String dob;
    private String gender;
    private String maritalStatus;
    private String nationality;
}

