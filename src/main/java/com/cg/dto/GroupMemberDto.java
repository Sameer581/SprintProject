package com.cg.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GroupMemberDto {

    @NotNull(message = "Group ID is required")
    @Min(value = 1, message = "Group ID must be valid")
    private Long groupId;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be valid")
    private Long userId;

    
    private String role;

    public GroupMemberDto() {
    }

    public GroupMemberDto(Long groupId, Long userId, String role) {
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}