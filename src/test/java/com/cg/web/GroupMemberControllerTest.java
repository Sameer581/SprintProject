package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
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
import com.cg.dto.GroupMemberDto;
import com.cg.service.GroupMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class GroupMemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupMemberService groupMemberService;

    @InjectMocks
    private GroupMemberController groupMemberController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private GroupMemberDto groupMemberDto;
    private GroupDto groupDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupMemberController).build();

        // ── GroupMemberDto setup ──────────────────────────────────────────────
        groupMemberDto = new GroupMemberDto();
        groupMemberDto.setGroupId(1L);
        groupMemberDto.setUserId(101L);

        // ── GroupDto setup ────────────────────────────────────────────────────
        groupDto = new GroupDto();
        groupDto.setGroupId(1L);
        groupDto.setGroupName("TestGroup");
    }

    // ─── POST /groups/{groupId}/members ──────────────────────────────────────

    @Test
    void testAddMember_success() throws Exception {
        when(groupMemberService.addMember(any(GroupMemberDto.class))).thenReturn(groupMemberDto);

        mockMvc.perform(post("/groups/1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupMemberDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("Member added successfully: userId "));
    }

    // ─── GET /groups/user/{userId}/groups ─────────────────────────────────────

    @Test
    void testGetUserGroups_success() throws Exception {
        List<GroupDto> groups = Arrays.asList(groupDto);
        when(groupMemberService.getGroupsByUserId(101L)).thenReturn(groups);

        mockMvc.perform(get("/groups/user/101/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupName").value("TestGroup"));
    }

    // ─── GET /groups/{groupId}/members/count ──────────────────────────────────

    @Test
    void testGetMemberCount_success() throws Exception {
        when(groupMemberService.getMemberCount(1L)).thenReturn(5L);

        mockMvc.perform(get("/groups/1/members/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // ─── GET /groups/name/{groupName}/members ─────────────────────────────────

    @Test
    void testGetMembersByGroupName_success() throws Exception {
        List<GroupMemberDto> members = Arrays.asList(groupMemberDto);
        when(groupMemberService.getMembersByGroupName("TestGroup")).thenReturn(members);

        mockMvc.perform(get("/groups/name/TestGroup/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(101));
    }

    // ─── DELETE /groups/{groupId}/members/{userId} ────────────────────────────

    @Test
    void testRemoveMember_success() throws Exception {
        doNothing().when(groupMemberService).removeMember(1L, 101L);

        mockMvc.perform(delete("/groups/1/members/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Member removed successfully: userId "));
    }

    // ─── GET /groups/{groupId}/members ────────────────────────────────────────

    @Test
    void testGetMembers_success() throws Exception {
        List<GroupMemberDto> members = Arrays.asList(groupMemberDto);
        when(groupMemberService.getMembersByGroup(1L)).thenReturn(members);

        mockMvc.perform(get("/groups/1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupId").value(1))
                .andExpect(jsonPath("$[0].userId").value(101));
    }
}