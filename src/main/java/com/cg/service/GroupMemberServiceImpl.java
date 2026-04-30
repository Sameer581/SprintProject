package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDto;
import com.cg.entity.Group;
import com.cg.entity.GroupMember;
import com.cg.entity.User;
import com.cg.exception.DuplicateResourceException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.exception.ValidationException;
import com.cg.repo.GroupMemberRepo;
import com.cg.repo.GroupRepo;
import com.cg.repo.UserRepo;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepo groupMemberRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    
    private GroupMemberDto toDto(GroupMember gm) {
        return new GroupMemberDto(
                gm.getGroup().getGroupId(),
                gm.getUser().getUserId(),
                gm.getRole()
        );
    }

 
    @Override
    public GroupMemberDto addMember(GroupMemberDto dto) {

        Group group = groupRepo.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", dto.getGroupId()));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", dto.getUserId()));

        
        if (groupMemberRepo.existsByGroupAndUser(group, user)) {
            throw new DuplicateResourceException("GroupMember", "userId", dto.getUserId());
        }

      
        String role = (dto.getRole() == null || dto.getRole().isEmpty()) ? "MEMBER" : dto.getRole();

        GroupMember gm = new GroupMember(group, user, role);

        return toDto(groupMemberRepo.save(gm));
    }

   
    @Override
    public void removeMember(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        if (!groupMemberRepo.existsByGroupAndUser(group, user)) {
            throw new ResourceNotFoundException("GroupMember", "userId", userId);
        }

        groupMemberRepo.deleteByGroupAndUser(group, user);
    }
    
    @Override
    public List<GroupMemberDto> getMembersByGroupName(String groupName) {

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new ValidationException("groupName", "Group name cannot be blank");
        }

        Group group = groupRepo.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupName", groupName));

        List<GroupMember> members = groupMemberRepo.findByGroup(group);

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "groupName", groupName);
        }

        return members.stream()
                .map(gm -> new GroupMemberDto(
                        gm.getGroup().getGroupId(),
                        gm.getUser().getUserId(),
                        gm.getRole()
                ))
                .toList();
    }
    
    
    @Override
    public List<GroupDto> getGroupsByUserId(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<GroupMember> memberships = groupMemberRepo.findByUser(user);

        if (memberships.isEmpty()) {
            throw new ResourceNotFoundException("Groups", "userId", userId);
        }

        return memberships.stream()
                .map(gm -> {
                    Group g = gm.getGroup();

                    // map to GroupDto
                    return new GroupDto(
                            g.getGroupId(),
                            g.getGroupName(),
                            g.getAdmin().getUserId(),
                            null
                    );
                })
                .toList();
    }
    
    
    @Override
    public long getMemberCount(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        return groupMemberRepo.countByGroup(group);
    }
    
    

   
    @Override
    public List<GroupMemberDto> getMembersByGroup(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        List<GroupMember> members = groupMemberRepo.findByGroup(group);

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "groupId", groupId);
        }

        return members.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


	
	}
