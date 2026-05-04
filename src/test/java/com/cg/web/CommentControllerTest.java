package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;
import com.cg.service.CommentsService;

import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentsService commentsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addComment_success() throws Exception {

        Long postId = 1L;

        CommentsDto input = new CommentsDto();
        input.setUserId(1L);
        input.setCommentText("Hello");

        CommentResponseDto response = new CommentResponseDto();
        response.setCommentId(5L);
        response.setCommentText("Hello");

        when(commentsService.addComment(eq(postId), any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").value(5));
    }
    @Test
    void addComment_validationFail() throws Exception {

        Long postId = 1L;

        CommentsDto invalid = new CommentsDto();
        invalid.setUserId(null);
        invalid.setCommentText("");

        mockMvc.perform(post("/api/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getComment_success() throws Exception {

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentId(1L);
        dto.setCommentText("Hello");

        when(commentsService.getComment(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(1));
    }
    @Test
    void getCommentsByPost_success() throws Exception {

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentId(1L);

        when(commentsService.getCommentsByPostId(1L))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test
    void updateComment_success() throws Exception {

        CommentUpdateDto input = new CommentUpdateDto();
        input.setCommentText("Updated");

        CommentResponseDto output = new CommentResponseDto();
        output.setCommentId(1L);
        output.setCommentText("Updated");

        when(commentsService.updateComment(eq(1L), any()))
                .thenReturn(output);

        mockMvc.perform(put("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(1));
    }
    @Test
    void deleteComment_success() throws Exception {

        doNothing().when(commentsService).deleteComment(1L);

        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isNoContent());
    }

    // ---------------- USER ----------------

    @Test
    void getCommentsByUser_success() throws Exception {

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentId(1L);

        when(commentsService.getCommentsByUserId(1L))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/users/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    

}