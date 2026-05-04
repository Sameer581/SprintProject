package com.cg.service;

import java.util.List;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDetailDto;
import com.cg.dto.GroupMemberDto;

public interface GroupMemberService {

    //GroupMemberDto addMember(GroupMemberDto dto);

    void removeMember(Long groupId, Long userId);

    List<GroupMemberDto> getMembersByGroup(Long groupId);
    
    List<GroupMemberDto> getMembersByGroupName(String groupName);
    
    List<GroupDto> getGroupsByUserId(Long userId);
    
    long getMemberCount(Long groupId);
    
    List<GroupMemberDetailDto> getMemberDetails(Long groupId);

    List<GroupMemberDetailDto> getMembersByRole(Long groupId, String role);

    GroupMemberDto updateMemberRole(Long groupId, Long userId, String newRole);

    void transferAdmin(Long groupId, Long currentAdminId, Long newAdminId);

    List<GroupMemberDetailDto> searchMembers(Long groupId, String keyword);
    
    GroupMemberDto requestToJoin(Long groupId, Long userId);

    void leaveGroup(Long groupId, Long userId);

    List<GroupMemberDto> getPendingRequests(Long groupId);

    GroupMemberDto approveRequest(Long groupId, Long userId);

    GroupMemberDto rejectRequest(Long groupId, Long userId);
    
    List<GroupMemberDetailDto> searchMembersByGroupName(String groupName, String keyword);
    
    GroupMemberDto addMember(Long groupId, GroupMemberDto dto);
    
    
}