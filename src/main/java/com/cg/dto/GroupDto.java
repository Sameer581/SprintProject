package com.cg.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GroupDto {

    private Integer groupId;

    @NotBlank(message = "Group name is required")
    @Size(min = 3, max = 255, message = "Group name must be between 3 and 255 characters")
    private String groupName;

    @NotNull(message = "Admin ID is required")
    @Min(value = 1, message = "Admin ID must be at least 1")
    private Integer adminId;
    
    

    public GroupDto() {
    }

    public GroupDto(Integer groupId, String groupName, Integer adminId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.adminId = adminId;

    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    
}