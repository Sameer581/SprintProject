package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.cg.dto.LikeDto;
import com.cg.service.LikeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private LikeDto likeDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();

        likeDto = new LikeDto();
        likeDto.setLikeId(1L);
        likeDto.setUserId(1L);
        likeDto.setPostId(10L);
        likeDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
        likeDto.setMessage("Post liked successfully");
        likeDto.setTotalLikes(5);
    }

    // POST /likes/toggle 

    @Test
    void testToggleLike_success() throws Exception {
        when(likeService.toggleLike(any(LikeDto.class))).thenReturn(likeDto);

        mockMvc.perform(post("/likes/toggle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(likeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likeId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.postId").value(10))
                .andExpect(jsonPath("$.message").value("Post liked successfully"))
                .andExpect(jsonPath("$.totalLikes").value(5));
    }

    // GET /likes/count/{postId}

    @Test
    void testCountLikesByPost_success() throws Exception {
        when(likeService.countLikesByPost(10L)).thenReturn(5);

        mockMvc.perform(get("/likes/count/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    //  GET /likes/check/{userId}/{postId} 

    @Test
    void testHasUserLiked_success() throws Exception {
        when(likeService.hasUserLiked(1L, 10L)).thenReturn(true);

        mockMvc.perform(get("/likes/check/1/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // GET /likes/post/{postId} 

    @Test
    void testGetLikesByPost_success() throws Exception {
        List<LikeDto> likes = Arrays.asList(likeDto);

        when(likeService.getLikesByPost(10L)).thenReturn(likes);

        mockMvc.perform(get("/likes/post/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].likeId").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].postId").value(10))
                .andExpect(jsonPath("$[0].message").value("Post liked successfully"));
    }

    //GET /likes/user/{userId} 

    @Test
    void testGetLikesByUser_success() throws Exception {
        List<LikeDto> likes = Arrays.asList(likeDto);

        when(likeService.getLikesByUser(1L)).thenReturn(likes);

        mockMvc.perform(get("/likes/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].likeId").value(1))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].postId").value(10))
                .andExpect(jsonPath("$[0].message").value("Post liked successfully"));
    }
}