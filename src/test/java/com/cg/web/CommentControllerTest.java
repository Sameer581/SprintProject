package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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

        CommentsDto input = new CommentsDto(1L, 1L, "Hello");

        when(commentsService.addComment(any())).thenReturn(5L);

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void addComment_validationFail() throws Exception {

        CommentsDto invalid = new CommentsDto(null, null, "");

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errormsg").value("validation failed"))
                .andExpect(jsonPath("$.errMap.postID").exists())
                .andExpect(jsonPath("$.errMap.userID").exists())
                .andExpect(jsonPath("$.errMap.commentText").exists());
    }

    @Test
    void getComment_success() throws Exception {

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentID(1L);
        dto.setCommentText("Hello");
        dto.setTimestamp(LocalDateTime.now());
        dto.setPostID(1L);
        dto.setUserID(1L);
        dto.setUsername("sameer");

        when(commentsService.getComment(1L)).thenReturn(dto);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentID").value(1))
                .andExpect(jsonPath("$.commentText").value("Hello"));
    }

    @Test
    void getAllComments_success() throws Exception {

        CommentResponseDto dto = new CommentResponseDto();
        dto.setCommentID(1L);
        dto.setCommentText("Hello");

        when(commentsService.getAllComments()).thenReturn(List.of(dto));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].commentID").value(1));
    }


    @Test
    void updateComment_success() throws Exception {

        CommentUpdateDto input = new CommentUpdateDto();
        input.setCommentText("Updated");

        CommentResponseDto output = new CommentResponseDto();
        output.setCommentID(1L);
        output.setCommentText("Updated");

        when(commentsService.updateComment(any(), any())).thenReturn(output);

        mockMvc.perform(put("/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentID").value(1))
                .andExpect(jsonPath("$.commentText").value("Updated"));
    }

    @Test
    void updateComment_validationFail() throws Exception {

        CommentUpdateDto invalid = new CommentUpdateDto();
        invalid.setCommentText("");

        mockMvc.perform(put("/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errormsg").value("validation failed"));
    }
}