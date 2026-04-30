/*package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cg.dto.GroupDto;
import com.cg.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private GroupDto groupDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();

        groupDto = new GroupDto();
        groupDto.setGroupId(1L);
        groupDto.setGroupName("Tech Enthusiasts");
        // groupDto.setAdminId(10L);
    }

    // ─── GET /groups/{groupId} ────────────────────────────────────────────────

    @Test
    void testGetGroup_success() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(groupDto);

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(1))
                .andExpect(jsonPath("$.groupName").value("Tech Enthusiasts"));
    }

    // ─── GET /groups/viewall ──────────────────────────────────────────────────

    @Test
    void testGetAllGroups_success() throws Exception {
        when(groupService.getAllGroups()).thenReturn(Arrays.asList(groupDto));

        mockMvc.perform(get("/groups/viewall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupName").value("Tech Enthusiasts"));
    }

    // ─── POST /groups/add ─────────────────────────────────────────────────────

    @Test
    void testAddGroup_success() throws Exception {
        when(groupService.createGroup(any(GroupDto.class))).thenReturn(groupDto);

        mockMvc.perform(post("/groups/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("Group created successfully with id "))
                .andExpect(jsonPath("$.id").value(1));
    }

    // ─── PUT /groups/update/{groupId} ─────────────────────────────────────────

    @Test
    void testUpdateGroup_success() throws Exception {
        when(groupService.updateGroup(eq(1L), any(GroupDto.class))).thenReturn(groupDto);

        mockMvc.perform(put("/groups/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Group updated successfully with id "))
                .andExpect(jsonPath("$.id").value(1));
    }

    // ─── DELETE /groups/delete/{groupId} ──────────────────────────────────────

    @Test
    void testDeleteGroup_success() throws Exception {
        doNothing().when(groupService).deleteGroup(1L);

        mockMvc.perform(delete("/groups/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Group deleted successfully with id "))
                .andExpect(jsonPath("$.id").value(1));
    }

    // ─── GET /groups/name/{groupName} ─────────────────────────────────────────

    @Test
    void testGetByName_success() throws Exception {
        when(groupService.getGroupByName("Tech Enthusiasts")).thenReturn(groupDto);

        mockMvc.perform(get("/groups/name/Tech Enthusiasts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(1))
                .andExpect(jsonPath("$.groupName").value("Tech Enthusiasts"));
    }

    // ─── GET /groups/admin/{adminId} ──────────────────────────────────────────

    @Test
    void testGetByAdmin_success() throws Exception {
        when(groupService.getGroupsByAdminId(10L)).thenReturn(Arrays.asList(groupDto));

        mockMvc.perform(get("/groups/admin/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupId").value(1))
                .andExpect(jsonPath("$[0].groupName").value("Tech Enthusiasts"));
    }
}*/



package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cg.dto.GroupDto;
//import com.cg.dto.SuccessMessageDto;
import com.cg.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private GroupDto groupDto;

    @BeforeEach
    void setUp() {
        // Use a no-op validator so @Valid annotations don't trigger
        // Bean Validation during unit tests — we test validation separately
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(groupController)
                .setValidator(validator)
                .build();

        groupDto = new GroupDto();
        groupDto.setGroupId(1L);
        groupDto.setGroupName("TestGroup");
        groupDto.setAdminId(10L);
    }

    // ─── GET /groups/{groupId} ────────────────────────────────────────────────

    @Test
    void testGetGroup_success() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(groupDto);

        mockMvc.perform(get("/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(1))
                .andExpect(jsonPath("$.groupName").value("TestGroup"));
    }

    // ─── GET /groups/viewall ──────────────────────────────────────────────────

    @Test
    void testGetAllGroups_success() throws Exception {
        List<GroupDto> groups = Arrays.asList(groupDto);
        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(get("/groups/viewall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupName").value("TestGroup"));
    }

    // ─── POST /groups/add ─────────────────────────────────────────────────────

    @Test
    void testAddGroup_success() throws Exception {
        when(groupService.createGroup(any(GroupDto.class))).thenReturn(groupDto);

        mockMvc.perform(post("/groups/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("Group created successfully with id "));
    }

    // ─── PUT /groups/update/{groupId} ─────────────────────────────────────────

    @Test
    void testUpdateGroup_success() throws Exception {
        when(groupService.updateGroup(eq(1L), any(GroupDto.class))).thenReturn(groupDto);

        mockMvc.perform(put("/groups/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Group updated successfully with id "));
    }

    // ─── DELETE /groups/delete/{groupId} ──────────────────────────────────────

    @Test
    void testDeleteGroup_success() throws Exception {
        doNothing().when(groupService).deleteGroup(1L);

        mockMvc.perform(delete("/groups/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Group deleted successfully with id "));
    }

    // ─── GET /groups/name/{groupName} ─────────────────────────────────────────

    @Test
    void testGetByName_success() throws Exception {
        when(groupService.getGroupByName("TestGroup")).thenReturn(groupDto);

        mockMvc.perform(get("/groups/name/TestGroup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(1))
                .andExpect(jsonPath("$.groupName").value("TestGroup"));
    }

    // ─── GET /groups/admin/{adminId} ──────────────────────────────────────────

    @Test
    void testGetByAdmin_success() throws Exception {
        List<GroupDto> groups = Arrays.asList(groupDto);
        when(groupService.getGroupsByAdminId(10L)).thenReturn(groups);

        mockMvc.perform(get("/groups/admin/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].adminId").value(10));
    }
}