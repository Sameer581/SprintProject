package com.cg.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GroupDto {

    private Long groupId;

    @NotBlank(message = "Group name is required")
    @Size(min = 3, max = 255, message = "Group name must be between 3 and 255 characters")
    private String groupName;

    @NotNull(message = "Admin ID is required")
    @Min(value = 1, message = "Admin ID must be at least 1")
    private Long adminId;

    
    private List<Long> memberIds;

    public GroupDto() {
    }

    public GroupDto(Long groupId, String groupName, Long adminId, List<Long> memberIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.adminId = adminId;
        this.memberIds = memberIds;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}