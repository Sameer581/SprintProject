package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cg.dto.FriendshipDto;
import com.cg.enums.FriendshipStatus;
import com.cg.service.FriendshipService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.anyLong;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class FriendshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FriendshipService friendshipService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void sendFriendRequest_success() throws Exception {
        FriendshipDto input = new FriendshipDto();
        input.setUserId1(1L);
        input.setUserId2(2L);

        FriendshipDto output = new FriendshipDto();
        output.setFriendshipId(1L);
        output.setUserId1(1L);
        output.setUserId2(2L);
        output.setStatus(FriendshipStatus.PENDING);

        when(friendshipService.sendFriendRequest(any(FriendshipDto.class))).thenReturn(output);

        mockMvc.perform(post("/api/friendships/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.friendshipId").value(1))
                .andExpect(jsonPath("$.userId1").value(1))
                .andExpect(jsonPath("$.userId2").value(2))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

   
    @Test
    void acceptFriendRequest_success() throws Exception {
        FriendshipDto response = new FriendshipDto();
        response.setFriendshipId(1L);
        response.setUserId1(1L);
        response.setUserId2(2L);
        response.setStatus(FriendshipStatus.ACCEPTED);

        when(friendshipService.acceptFriendRequest(1L))
                .thenReturn(response);

        mockMvc.perform(put("/api/friendships/1/accept"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.friendshipId").value(1))
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }
    

//    @Test
//    void rejectFriendRequest_success() throws Exception {
//        FriendshipDto response = new FriendshipDto();
//        response.setFriendshipId(1L);
//        response.setStatus(FriendshipStatus.REJECTED);
//
//        when(friendshipService.rejectFriendRequest(1L)).thenReturn(response);
//
//        mockMvc.perform(patch("/api/friendships/1/reject"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.friendshipId").value(1))
//                .andExpect(jsonPath("$.status").value("REJECTED"));
//    }

   
    @Test
    void getPendingRequests_success() throws Exception {
        FriendshipDto dto = new FriendshipDto();
        dto.setFriendshipId(1L);
        dto.setUserId1(1L);
        dto.setUserId2(2L);
        dto.setStatus(FriendshipStatus.PENDING);

        when(friendshipService.getPendingRequests(2L))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/friendships/users/2/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].friendshipId").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
  
    
    @Test
    void checkFriendshipStatus_success() throws Exception {
        FriendshipDto dto = new FriendshipDto();
        dto.setFriendshipId(1L);
        dto.setUserId1(1L);
        dto.setUserId2(2L);
        dto.setStatus(FriendshipStatus.ACCEPTED);

        when(friendshipService.checkFriendship(anyLong(), anyLong()))
                .thenReturn(dto);

        mockMvc.perform(get("/api/friendships/check")
                .param("userId1", "1")
                .param("userId2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }
    

    @Test
    void getFriendsByUserId_success() throws Exception {
        FriendshipDto dto = new FriendshipDto();
        dto.setFriendshipId(1L);
        dto.setUserId1(1L);
        dto.setUserId2(2L);
        dto.setStatus(FriendshipStatus.ACCEPTED);

        when(friendshipService.getFriendsByUserId(1L))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/friendships/users/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].friendshipId").value(1))
                .andExpect(jsonPath("$[0].status").value("ACCEPTED"));
    }
    
    
}
