package com.vagarious.backend.hrms.model;

import jakarta.persistence.*;

@Entity
public class BankDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String accountNumber;
    private String bankName;
    private String ifsc;
    private String branch;
}
