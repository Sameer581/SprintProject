package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.GroupDto;
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
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GroupMemberRepo groupMemberRepo;

    
    private GroupDto toDto(Group group) {

        List<Long> memberIds = groupMemberRepo.findByGroup(group)
                .stream()
                .map(gm -> gm.getUser().getUserId())
                .collect(Collectors.toList());

        return new GroupDto(
                group.getGroupId(),
                group.getGroupName(),
                group.getAdmin().getUserId(),
                memberIds
        );
    }

    
    private Group toEntity(GroupDto dto) {
        User admin = userRepo.findById(dto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "adminId", dto.getAdminId()));

        return new Group(dto.getGroupId(), dto.getGroupName(), admin);
    }

    
    @Override
    public GroupDto createGroup(GroupDto groupDto) {

        if (groupDto.getGroupName() == null || groupDto.getGroupName().trim().isEmpty()) {
            throw new ValidationException("groupName", "Group name cannot be blank");
        }

        if (groupRepo.findByGroupName(groupDto.getGroupName()).isPresent()) {
            throw new DuplicateResourceException("Group", "groupName", groupDto.getGroupName());
        }

       
        Group savedGroup = groupRepo.save(toEntity(groupDto));
        User admin = savedGroup.getAdmin();

        GroupMember gm = new GroupMember();
        gm.setGroup(savedGroup);
        gm.setUser(admin);
        gm.setRole("ADMIN");

        groupMemberRepo.save(gm);

        return toDto(savedGroup);
    }

    
    @Override
    public GroupDto getGroupById(Long groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        return toDto(group);
    }

    
    @Override
    public GroupDto getGroupByName(String groupName) {

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new ValidationException("groupName", "Group name cannot be blank");
        }

        Group group = groupRepo.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupName", groupName));

        return toDto(group);
    }

    
    @Override
    public List<GroupDto> getAllGroups() {
        List<Group> groups = groupRepo.findAll();

        if (groups.isEmpty()) {
            throw new ResourceNotFoundException("Groups", "database", "no records found");
        }

        return groups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    
    @Override
    public List<GroupDto> getGroupsByAdminId(Long adminId) {

        userRepo.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "adminId", adminId));

        List<Group> groups = groupRepo.findByAdminUserId(adminId);

        if (groups.isEmpty()) {
            throw new ResourceNotFoundException("Groups", "adminId", adminId);
        }

        return groups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

   
    @Override
    public GroupDto updateGroup(Long groupId, GroupDto groupDto) {

        Group existing = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        if (!existing.getGroupName().equals(groupDto.getGroupName())) {
            if (groupRepo.findByGroupName(groupDto.getGroupName()).isPresent()) {
                throw new DuplicateResourceException("Group", "groupName", groupDto.getGroupName());
            }
        }

        User admin = userRepo.findById(groupDto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "adminId", groupDto.getAdminId()));

        existing.setGroupName(groupDto.getGroupName());
        existing.setAdmin(admin);

        return toDto(groupRepo.save(existing));
    }

 
    @Override
    public void deleteGroup(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        
        List<GroupMember> members = groupMemberRepo.findByGroup(group);
        groupMemberRepo.deleteAll(members);

        groupRepo.delete(group);
    }
}