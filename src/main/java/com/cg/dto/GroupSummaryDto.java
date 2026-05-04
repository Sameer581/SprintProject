package com.cg.dto;

import java.util.Map;

public class GroupSummaryDto {

    private Long groupId;
    private String groupName;
    private Long adminId;
    private String adminName;
    private long totalMembers;
    private Map<String, Long> roleCounts;

    public GroupSummaryDto() {}

    public GroupSummaryDto(Long groupId, String groupName, Long adminId,
                           String adminName, long totalMembers,
                           Map<String, Long> roleCounts) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.adminId = adminId;
        this.adminName = adminName;
        this.totalMembers = totalMembers;
        this.roleCounts = roleCounts;
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

    public String getAdminName() { 
        return adminName; 
        }
    public void setAdminName(String adminName) { 
        this.adminName = adminName;
         }

    public long getTotalMembers() { 
        return totalMembers; 
        }
    public void setTotalMembers(long totalMembers) { 
        this.totalMembers = totalMembers;
         }

    public Map<String, Long> getRoleCounts() { 
        return roleCounts; 
        }
    public void setRoleCounts(Map<String, Long> roleCounts) { 
        this.roleCounts = roleCounts; 
        }
}