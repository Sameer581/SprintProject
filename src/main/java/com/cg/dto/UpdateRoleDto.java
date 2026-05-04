package com.cg.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateRoleDto {

    @NotBlank(message = "Role cannot be blank")
    private String role;

    public UpdateRoleDto() {}

    public UpdateRoleDto(String role) {
        this.role = role;
    }

    public String getRole() { 
        return role; 
        }
    public void setRole(String role) { 
        this.role = role; 
        }
}