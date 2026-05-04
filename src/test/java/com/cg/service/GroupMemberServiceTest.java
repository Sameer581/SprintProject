package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
public class GroupMemberServiceTest {

    @Mock
    private GroupMemberRepo groupMemberRepo;

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private GroupMemberServiceImpl service;

    private User adminUser;
    private User regularUser;
    private Group group;
    private GroupMember activeMember;
    private GroupMember pendingMember;
    private GroupMemberDto memberDto;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setUserId(1L);
        adminUser.setUsername("adminUser");
        adminUser.setEmail("admin@test.com");

        regularUser = new User();
        regularUser.setUserId(2L);
        regularUser.setUsername("regularUser");
        regularUser.setEmail("user@test.com");

        group = new Group();
        group.setGroupId(100L);
        group.setGroupName("Test Group");
        group.setAdmin(adminUser);

        activeMember = new GroupMember(group, regularUser, "MEMBER", "ACTIVE");
        
        pendingMember = new GroupMember(group, regularUser, "MEMBER", "PENDING");

        memberDto = new GroupMemberDto(2L, "MEMBER", "ACTIVE");
    }

    

    @Test
    void testAddMember_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "ACTIVE")).thenReturn(false);
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "PENDING")).thenReturn(false);
        when(groupMemberRepo.save(any(GroupMember.class))).thenReturn(activeMember);

        GroupMemberDto result = service.addMember(100L, memberDto);

        assertNotNull(result);
        assertEquals(2L, result.getUserId());
        assertEquals("ACTIVE", result.getStatus());
        verify(groupMemberRepo, times(1)).save(any(GroupMember.class));
    }

    @Test
    void testAddMember_duplicate() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "ACTIVE")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            service.addMember(100L, memberDto);
        });
    }

    

    @Test
    void testRemoveMember_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "ACTIVE"))
                .thenReturn(Optional.of(activeMember));

        service.removeMember(100L, 2L);

        verify(groupMemberRepo, times(1)).delete(activeMember);
    }

    @Test
    void testRemoveMember_notFound() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "ACTIVE"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.removeMember(100L, 2L);
        });
    }

    

    @Test
    void testGetMembersByGroupName_success() {
        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroupAndStatus(group, "ACTIVE")).thenReturn(Arrays.asList(activeMember));

        List<GroupMemberDto> result = service.getMembersByGroupName("Test Group");

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getUserId());
    }

    @Test
    void testGetMembersByGroupName_blankName() {
        assertThrows(ValidationException.class, () -> {
            service.getMembersByGroupName("  ");
        });
    }

    

    @Test
    void testGetGroupsByUserId_success() {
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByUserAndStatus(regularUser, "ACTIVE")).thenReturn(Arrays.asList(activeMember));

        List<GroupDto> result = service.getGroupsByUserId(2L);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getGroupId());
        assertEquals("Test Group", result.get(0).getGroupName());
    }

    

    @Test
    void testGetMemberCount_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.countByGroupAndStatus(group, "ACTIVE")).thenReturn(5L);

        long count = service.getMemberCount(100L);

        assertEquals(5L, count);
    }

    

    @Test
    void testUpdateMemberRole_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "ACTIVE"))
                .thenReturn(Optional.of(activeMember));
        when(groupMemberRepo.save(any(GroupMember.class))).thenReturn(activeMember);

        GroupMemberDto result = service.updateMemberRole(100L, 2L, "MODERATOR");

        assertEquals("MODERATOR", activeMember.getRole());
        verify(groupMemberRepo, times(1)).save(activeMember);
    }

    

    @Test
    void testTransferAdmin_success() {
        GroupMember adminGm = new GroupMember(group, adminUser, "ADMIN", "ACTIVE");
        
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "ACTIVE")).thenReturn(true);
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, adminUser, "ACTIVE")).thenReturn(Optional.of(adminGm));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "ACTIVE")).thenReturn(Optional.of(activeMember));

        service.transferAdmin(100L, 1L, 2L);

        assertEquals("MEMBER", adminGm.getRole());
        assertEquals("ADMIN", activeMember.getRole());
        assertEquals(2L, group.getAdmin().getUserId());
        
        verify(groupMemberRepo, times(2)).save(any(GroupMember.class));
        verify(groupRepo, times(1)).save(group);
    }

    @Test
    void testTransferAdmin_notCurrentAdmin() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));

        assertThrows(ValidationException.class, () -> {
            // Attempting to transfer with user 2 as current admin, but group admin is user 1
            service.transferAdmin(100L, 2L, 3L);
        });
    }

    

    @Test
    void testSearchMembers_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroupAndStatus(group, "ACTIVE")).thenReturn(Arrays.asList(activeMember));

        List<GroupMemberDetailDto> result = service.searchMembers(100L, "regular");

        assertEquals(1, result.size());
        assertEquals("regularUser", result.get(0).getName());
    }

    @Test
    void testSearchMembers_notFound() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroupAndStatus(group, "ACTIVE")).thenReturn(Arrays.asList(activeMember));

        assertThrows(ResourceNotFoundException.class, () -> {
            service.searchMembers(100L, "nonexistent");
        });
    }

    

    @Test
    void testRequestToJoin_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "ACTIVE")).thenReturn(false);
        when(groupMemberRepo.existsByGroupAndUserAndStatus(group, regularUser, "PENDING")).thenReturn(false);
        when(groupMemberRepo.save(any(GroupMember.class))).thenReturn(pendingMember);

        GroupMemberDto result = service.requestToJoin(100L, 2L);

        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testApproveRequest_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "PENDING"))
                .thenReturn(Optional.of(pendingMember));
        when(groupMemberRepo.save(any(GroupMember.class))).thenReturn(activeMember);

        GroupMemberDto result = service.approveRequest(100L, 2L);

        assertEquals("ACTIVE", pendingMember.getStatus()); // Modified before save
        verify(groupMemberRepo, times(1)).save(pendingMember);
    }

   

    @Test
    void testLeaveGroup_success() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(2L)).thenReturn(Optional.of(regularUser));
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, regularUser, "ACTIVE"))
                .thenReturn(Optional.of(activeMember));

        service.leaveGroup(100L, 2L);

        assertEquals("LEFT", activeMember.getStatus());
        verify(groupMemberRepo, times(1)).save(activeMember);
    }

    @Test
    void testLeaveGroup_adminCannotLeave() {
        when(groupRepo.findById(100L)).thenReturn(Optional.of(group));
        when(userRepo.findById(1L)).thenReturn(Optional.of(adminUser));
        
        GroupMember adminGm = new GroupMember(group, adminUser, "ADMIN", "ACTIVE");
        when(groupMemberRepo.findByGroupAndUserAndStatus(group, adminUser, "ACTIVE"))
                .thenReturn(Optional.of(adminGm));

        assertThrows(ValidationException.class, () -> {
            service.leaveGroup(100L, 1L); // Admin trying to leave
        });
    }
}