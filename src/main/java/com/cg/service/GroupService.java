package com.cg.service;

import java.util.List;
import com.cg.dto.GroupDto;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);

    GroupDto getGroupById(Long groupId);

    GroupDto getGroupByName(String groupName);

    List<GroupDto> getAllGroups();

    List<GroupDto> getGroupsByAdminId(Long adminId);

    GroupDto updateGroup(Long groupId, GroupDto groupDto);

    void deleteGroup(Long groupId);
}