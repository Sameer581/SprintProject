package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private GroupMemberRepo groupMemberRepo;

    @InjectMocks
    private GroupServiceImpl service;

    private User admin;
    private Group group;
    private GroupMember groupMember;

    @BeforeEach
    void setUp() {
        admin = new User();
        admin.setUserId(1L);

        group = new Group();
        group.setGroupId(1L);
        group.setGroupName("Test Group");
        group.setAdmin(admin);

        groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setUser(admin);
        groupMember.setRole("ADMIN");
    }

    

    @Test
    void testCreateGroup_success() {
        GroupDto dto = new GroupDto(null, "Test Group", 1L, null);

        when(userRepo.findById(1L)).thenReturn(Optional.of(admin));
        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.empty());
        when(groupRepo.save(any(Group.class))).thenReturn(group);
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        GroupDto result = service.createGroup(dto);

        assertEquals("Test Group", result.getGroupName());
        verify(groupRepo, times(1)).save(any(Group.class));
        verify(groupMemberRepo, times(1)).save(any(GroupMember.class));
    }

    @Test
    void testCreateGroup_blankName() {
        GroupDto dto = new GroupDto(null, "", 1L, null);

        assertThrows(ValidationException.class, () -> {
            service.createGroup(dto);
        });
    }

    @Test
    void testCreateGroup_duplicateName() {
        GroupDto dto = new GroupDto(null, "Test Group", 1L, null);

        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.of(group));

        assertThrows(DuplicateResourceException.class, () -> {
            service.createGroup(dto);
        });
    }

   
    @Test
    void testGetGroupById_success() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        GroupDto result = service.getGroupById(1L);

        assertEquals(1L, result.getGroupId());
    }

    @Test
    void testGetGroupById_notFound() {
        when(groupRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getGroupById(1L);
        });
    }

  
    @Test
    void testGetGroupByName_success() {
        when(groupRepo.findByGroupName("Test Group")).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        GroupDto result = service.getGroupByName("Test Group");

        assertEquals("Test Group", result.getGroupName());
    }

    @Test
    void testGetGroupByName_blank() {
        assertThrows(ValidationException.class, () -> {
            service.getGroupByName("");
        });
    }

  

    @Test
    void testGetAllGroups_success() {
        when(groupRepo.findAll()).thenReturn(Arrays.asList(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        assertEquals(1, service.getAllGroups().size());
    }

    @Test
    void testGetAllGroups_empty() {
        when(groupRepo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getAllGroups();
        });
    }



    @Test
    void testGetGroupsByAdminId_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(admin));
        when(groupRepo.findByAdminUserId(1L)).thenReturn(Arrays.asList(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        assertEquals(1, service.getGroupsByAdminId(1L).size());
    }

    @Test
    void testGetGroupsByAdminId_userNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getGroupsByAdminId(1L);
        });
    }

    

    @Test
    void testUpdateGroup_success() {
        GroupDto dto = new GroupDto(1L, "Updated Name", 1L, null);

        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupRepo.findByGroupName("Updated Name")).thenReturn(Optional.empty());
        when(userRepo.findById(1L)).thenReturn(Optional.of(admin));
        when(groupRepo.save(any(Group.class))).thenReturn(group);
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        GroupDto result = service.updateGroup(1L, dto);

        assertEquals("Updated Name", result.getGroupName());
    }

    @Test
    void testUpdateGroup_duplicateName() {
        GroupDto dto = new GroupDto(1L, "New Name", 1L, null);

        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupRepo.findByGroupName("New Name")).thenReturn(Optional.of(group));

        assertThrows(DuplicateResourceException.class, () -> {
            service.updateGroup(1L, dto);
        });
    }

 

    @Test
    void testDeleteGroup_success() {
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupMemberRepo.findByGroup(group)).thenReturn(Arrays.asList(groupMember));

        service.deleteGroup(1L);

        verify(groupMemberRepo, times(1)).deleteAll(any());
        verify(groupRepo, times(1)).delete(group);
    }

    @Test
    void testDeleteGroup_notFound() {
        when(groupRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteGroup(1L);
        });
    }
}