package com.vagarious.backend.hrms.dto;


import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	  private String currentPassword;
	    private String newPassword;
}
