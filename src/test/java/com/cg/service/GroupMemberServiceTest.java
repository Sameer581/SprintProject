package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

//import com.cg.dto.GroupDto;
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
//import com.cg.service.GroupMemberServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private Group group;
    private User user;
    private GroupMember groupMember;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        group = new Group();
        group.setGroupId(1L);
        group.setGroupName("Test Group");
        group.setAdmin(user);

        groupMember = new GroupMember(group, user, "ADMIN");
    }

   

    @Test
    void testAddMember_success() {
        GroupMemberDto dto = new GroupMemberDto(1L, 1L, "ADMIN");

        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.existsByGroupAndUser(group, user)).thenReturn(false);
        when(groupMemberRepo.save(any(GroupMember.class))).thenReturn(groupMember);

        GroupMemberDto result = service.addMember(dto);

        assertEquals(1L, result.getGroupId());
        assertEquals("ADMIN", result.getRole());

        verify(groupMemberRepo, times(1)).save(any(GroupMember.class));
    }

    @Test
    void testAddMember_duplicate() {
        GroupMemberDto dto = new GroupMemberDto(1L, 1L, "ADMIN");

        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.existsByGroupAndUser(group, user)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            service.addMember(dto);
        });
    }

   

    @Test
    void testRemoveMember_success() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.existsByGroupAndUser(group, user)).thenReturn(true);

        service.removeMember(1L, 1L);

        verify(groupMemberRepo, times(1)).deleteByGroupAndUser(group, user);
    }

    @Test
    void testRemoveMember_notFound() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.existsByGroupAndUser(group, user)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.removeMember(1L, 1L);
        });
    }

    

    @Test
    void testGetMembersByGroupName_success() {
        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        assertEquals(1, service.getMembersByGroupName("Test Group").size());
    }

    @Test
    void testGetMembersByGroupName_blank() {
        assertThrows(ValidationException.class, () -> {
            service.getMembersByGroupName("");
        });
    }

    @Test
    void testGetMembersByGroupName_notFound() {
        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getMembersByGroupName("Test Group");
        });
    }


    @Test
    void testGetGroupsByUserId_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.findByUser(user)).thenReturn(Arrays.asList(groupMember));

        assertEquals(1, service.getGroupsByUserId(1L).size());
    }

    @Test
    void testGetGroupsByUserId_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(groupMemberRepo.findByUser(user)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getGroupsByUserId(1L);
        });
    }

    

    @Test
    void testGetMemberCount_success() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.countByGroup(group)).thenReturn(5L);

        long count = service.getMemberCount(1L);

        assertEquals(5L, count);
    }

    @Test
    void testGetMemberCount_groupNotFound() {
        when(groupRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getMemberCount(1L);
        });
    }

   

    @Test
    void testGetMembersByGroup_success() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        assertEquals(1, service.getMembersByGroup(1L).size());
    }

    @Test
    void testGetMembersByGroup_empty() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getMembersByGroup(1L);
        });
    }
}