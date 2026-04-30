package com.cg.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupDto(
		@NotNull
		@Size(min=1, message ="username cannot be empty")
		@Size(max=30, message ="username should not be more than 30 characters")
		String username, 
		
		@NotNull
		@Size(min=1, message ="password cannot be empty")
		@Size(max=30, message ="password should not be more than 30 characters")
		String password, 
		
		@NotNull
		@Size(min=1, message ="customer name cannot be empty")
		@Size(max=30, message ="customer name should not be more than 30 characters")
		String custName, 
		
		@NotNull
		@Size(min=10, message="phone number should be only 10 characters long")
		@Size(max=10, message="phone number should be only 10 characters long")
		String phoneNo) {
}
