package com.cg.service;

import java.util.List;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDto;

public interface GroupMemberService {

    GroupMemberDto addMember(GroupMemberDto dto);

    void removeMember(Long groupId, Long userId);

    List<GroupMemberDto> getMembersByGroup(Long groupId);
    
    List<GroupMemberDto> getMembersByGroupName(String groupName);
    
    List<GroupDto> getGroupsByUserId(Long userId);
    
    long getMemberCount(Long groupId);
}