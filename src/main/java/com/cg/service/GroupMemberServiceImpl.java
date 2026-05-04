package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.GroupDto;
import com.cg.dto.GroupMemberDetailDto;
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
                //gm.getGroup().getGroupId(),
                gm.getUser().getUserId(),
                gm.getRole(),
                gm.getStatus()
        );
    }
    /*
    @Override
    public GroupMemberDto addMember(GroupMemberDto dto) {

        Group group = groupRepo.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", dto.getGroupId()));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", dto.getUserId()));

        // Block only if ACTIVE or PENDING — allow re-add after REJECTED or LEFT
        if (groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "ACTIVE") ||
            groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "PENDING")) {
            throw new DuplicateResourceException("GroupMember", "userId", dto.getUserId());
        }

        String role = (dto.getRole() == null || dto.getRole().isEmpty()) ? "MEMBER" : dto.getRole();

        GroupMember gm = new GroupMember(group, user, role, "ACTIVE");

        return toDto(groupMemberRepo.save(gm));
    }
    */
    @Override
    public GroupMemberDto addMember(Long groupId, GroupMemberDto dto) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", dto.getUserId()));

        if (groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "ACTIVE") ||
            groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "PENDING")) {
            throw new DuplicateResourceException("GroupMember", "userId", dto.getUserId());
        }

        String role = (dto.getRole() == null || dto.getRole().isEmpty())
                ? "MEMBER"
                : dto.getRole();

        GroupMember gm = new GroupMember(group, user, role, "ACTIVE");

        return toDto(groupMemberRepo.save(gm));
    }

    @Override
    public void removeMember(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        GroupMember gm = groupMemberRepo.findByGroupAndUserAndStatus(group, user, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException("GroupMember", "userId", userId));

        groupMemberRepo.delete(gm);
    }

    @Override
    public List<GroupMemberDto> getMembersByGroupName(String groupName) {

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new ValidationException("groupName", "Group name cannot be blank");
        }

        Group group = groupRepo.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupName", groupName));

        List<GroupMember> members = groupMemberRepo.findByGroupAndStatus(group, "ACTIVE");

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "groupName", groupName);
        }

        return members.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<GroupDto> getGroupsByUserId(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<GroupMember> memberships = groupMemberRepo.findByUserAndStatus(user, "ACTIVE");

        if (memberships.isEmpty()) {
            throw new ResourceNotFoundException("Groups", "userId", userId);
        }

        return memberships.stream()
                .map(gm -> {
                    Group g = gm.getGroup();
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

        return groupMemberRepo.countByGroupAndStatus(group, "ACTIVE");
    }

    @Override
    public List<GroupMemberDto> getMembersByGroup(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        List<GroupMember> members = groupMemberRepo.findByGroupAndStatus(group, "ACTIVE");

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "groupId", groupId);
        }

        return members.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupMemberDetailDto> getMemberDetails(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        List<GroupMember> members = groupMemberRepo.findByGroupAndStatus(group, "ACTIVE");

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "groupId", groupId);
        }

        return members.stream()
                .map(gm -> new GroupMemberDetailDto(
                        gm.getUser().getUserId(),
                        gm.getUser().getUsername(),
                        gm.getUser().getEmail(),
                        gm.getRole()
                ))
                .toList();
    }

    @Override
    public List<GroupMemberDetailDto> getMembersByRole(Long groupId, String role) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        List<GroupMember> members = groupMemberRepo
                .findByGroupAndRoleAndStatus(group, role.toUpperCase(), "ACTIVE");

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "role", role);
        }

        return members.stream()
                .map(gm -> new GroupMemberDetailDto(
                        gm.getUser().getUserId(),
                        gm.getUser().getUsername(),
                        gm.getUser().getEmail(),
                        gm.getRole()
                ))
                .toList();
    }

    @Override
    public GroupMemberDto updateMemberRole(Long groupId, Long userId, String newRole) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        GroupMember gm = groupMemberRepo.findByGroupAndUserAndStatus(group, user, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException("GroupMember", "userId", userId));

        gm.setRole(newRole.toUpperCase());
        return toDto(groupMemberRepo.save(gm));
    }

    @Override
    public void transferAdmin(Long groupId, Long currentAdminId, Long newAdminId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        if (!group.getAdmin().getUserId().equals(currentAdminId)) {
            throw new ValidationException("adminId", "Only the current admin can transfer ownership");
        }

        User newAdmin = userRepo.findById(newAdminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", newAdminId));

        
        if (!groupMemberRepo.existsByGroupAndUserAndStatus(group, newAdmin, "ACTIVE")) {
            throw new ResourceNotFoundException("GroupMember", "userId", newAdminId);
        }

        
        User oldAdmin = group.getAdmin();
        groupMemberRepo.findByGroupAndUserAndStatus(group, oldAdmin, "ACTIVE").ifPresent(gm -> {
            gm.setRole("MEMBER");
            groupMemberRepo.save(gm);
        });

        
        groupMemberRepo.findByGroupAndUserAndStatus(group, newAdmin, "ACTIVE").ifPresent(gm -> {
            gm.setRole("ADMIN");
            groupMemberRepo.save(gm);
        });

        group.setAdmin(newAdmin);
        groupRepo.save(group);
    }

    @Override
    public List<GroupMemberDetailDto> searchMembers(Long groupId, String keyword) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("keyword", "Search keyword cannot be blank");
        }

        String lower = keyword.toLowerCase();

        List<GroupMember> members = groupMemberRepo.findByGroupAndStatus(group, "ACTIVE");

        List<GroupMemberDetailDto> result = members.stream()
                .filter(gm ->
                        gm.getUser().getUsername().toLowerCase().contains(lower) ||
                        gm.getUser().getEmail().toLowerCase().contains(lower))
                .map(gm -> new GroupMemberDetailDto(
                        gm.getUser().getUserId(),
                        gm.getUser().getUsername(),
                        gm.getUser().getEmail(),
                        gm.getRole()
                ))
                .toList();

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "keyword", keyword);
        }

        return result;
    }

    @Override
    public GroupMemberDto requestToJoin(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        if (groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "ACTIVE") ||
            groupMemberRepo.existsByGroupAndUserAndStatus(group, user, "PENDING")) {
            throw new DuplicateResourceException("GroupMember", "userId", userId);
        }

        GroupMember gm = new GroupMember(group, user, "MEMBER", "PENDING");
        return toDto(groupMemberRepo.save(gm));
    }

    @Override
    public void leaveGroup(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        GroupMember gm = groupMemberRepo.findByGroupAndUserAndStatus(group, user, "ACTIVE")
                .orElseThrow(() -> new ResourceNotFoundException("GroupMember", "userId", userId));

        if (group.getAdmin().getUserId().equals(userId)) {
            throw new ValidationException("userId",
                    "Admin cannot leave the group. Transfer admin role first.");
        }

        gm.setStatus("LEFT");
        groupMemberRepo.save(gm);
    }

    @Override
    public List<GroupMemberDto> getPendingRequests(Long groupId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        List<GroupMember> pending = groupMemberRepo.findByGroupAndStatus(group, "PENDING");

        if (pending.isEmpty()) {
            throw new ResourceNotFoundException("JoinRequests", "groupId", groupId);
        }

        return pending.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public GroupMemberDto approveRequest(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        GroupMember gm = groupMemberRepo.findByGroupAndUserAndStatus(group, user, "PENDING")
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "userId", userId));

        gm.setStatus("ACTIVE");
        return toDto(groupMemberRepo.save(gm));
    }

    @Override
    public GroupMemberDto rejectRequest(Long groupId, Long userId) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupId", groupId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        GroupMember gm = groupMemberRepo.findByGroupAndUserAndStatus(group, user, "PENDING")
                .orElseThrow(() -> new ResourceNotFoundException("JoinRequest", "userId", userId));

        gm.setStatus("REJECTED");
        return toDto(groupMemberRepo.save(gm));
    }
    
    @Override
    public List<GroupMemberDetailDto> searchMembersByGroupName(String groupName, String keyword) {

        if (groupName == null || groupName.trim().isEmpty()) {
            throw new ValidationException("groupName", "Group name cannot be blank");
        }

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("keyword", "Search keyword cannot be blank");
        }

        Group group = groupRepo.findByGroupName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "groupName", groupName));

        String lower = keyword.toLowerCase();

        List<GroupMemberDetailDto> result = groupMemberRepo.findByGroupAndStatus(group, "ACTIVE")
                .stream()
                .filter(gm -> gm.getUser().getUsername().toLowerCase().contains(lower))
                .map(gm -> new GroupMemberDetailDto(
                        gm.getUser().getUserId(),
                        gm.getUser().getUsername(),
                        gm.getUser().getEmail(),
                        gm.getRole()
                ))
                .toList();

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("GroupMembers", "keyword", keyword);
        }

        return result;
    }
    
}