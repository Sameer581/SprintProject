package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;
import com.cg.entity.Comments;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.CommentsRepo;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {

    @Mock
    private CommentsRepo commentsRepo;

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CommentsServiceImpl service;

    private Post post;
    private User user;
    private Comments comment;

    @BeforeEach
    void setUp() {

        post = new Post();
        post.setPostId(1L);
        post.setContent("Test Post");

        user = new User();
        user.setUserId(1L);
        user.setUsername("sameer");

        comment = new Comments();
        comment.setCommentId(1L);
        comment.setCommentText("Hello");
        comment.setPost(post);
        comment.setUser(user);
        comment.setTimestamp(LocalDateTime.now());
    }

    @Test
    void testAddComment_success() {
        Long postId = 1L;
        CommentsDto dto = new CommentsDto();
        dto.setUserId(1L);
        dto.setCommentText("Hello");

        when(postRepo.findById(postId)).thenReturn(Optional.of(post));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(commentsRepo.save(any(Comments.class))).thenReturn(comment);

        CommentResponseDto result = service.addComment(postId, dto);
        assertEquals(1L, result.getCommentId());
        assertEquals("Hello", result.getCommentText());
        verify(commentsRepo, times(1)).save(any());
    }

    @Test
    void testAddComment_postNotFound() {

        Long postId = 1L;
        CommentsDto dto = new CommentsDto();
        dto.setUserId(1L);
        dto.setCommentText("Hello");

        when(postRepo.findById(postId)).thenReturn(Optional.empty());
        assertThrows(NotAvailableException.class, () ->
                service.addComment(postId, dto)
        );
    }

    @Test
    void testAddComment_userNotFound() {

        Long postId = 1L;
        CommentsDto dto = new CommentsDto();
        dto.setUserId(1L);
        dto.setCommentText("Hello");

        when(postRepo.findById(postId)).thenReturn(Optional.of(post));
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () ->
                service.addComment(postId, dto)
        );
    }

    @Test
    void testGetComment_success() {

        when(commentsRepo.findById(1L)).thenReturn(Optional.of(comment));
        CommentResponseDto result = service.getComment(1L);

        assertEquals("Hello", result.getCommentText());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testGetComment_notFound() {

        when(commentsRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () ->
                service.getComment(1L)
        );
    }

    @Test
    void testUpdateComment_success() {

        CommentUpdateDto dto = new CommentUpdateDto();
        dto.setCommentText("Updated");

        when(commentsRepo.findById(1L)).thenReturn(Optional.of(comment));
        when(commentsRepo.save(any(Comments.class))).thenReturn(comment);

        CommentResponseDto result = service.updateComment(1L, dto);

        assertEquals("Updated", result.getCommentText());
    }

    @Test
    void testUpdateComment_notFound() {

        CommentUpdateDto dto = new CommentUpdateDto();
        dto.setCommentText("Updated");

        when(commentsRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () ->
                service.updateComment(1L, dto)
        );
    }

    @Test
    void testDeleteComment_success() {

        when(commentsRepo.existsById(1L)).thenReturn(true);

        service.deleteComment(1L);

        verify(commentsRepo).deleteById(1L);
    }

    @Test
    void testDeleteComment_notFound() {

        when(commentsRepo.existsById(1L)).thenReturn(false);

        assertThrows(NotAvailableException.class, () ->
                service.deleteComment(1L)
        );
    }

    @Test
    void testGetCommentsByPostId() {

        when(commentsRepo.findByPost_PostId(1L)).thenReturn(List.of(comment));

        List<CommentResponseDto> result = service.getCommentsByPostId(1L);

        assertEquals(1, result.size());
        assertEquals("Hello", result.get(0).getCommentText());
    }
    
    @Test
    void testGetCommentsByUserId() {

        when(commentsRepo.findByUser_UserId(1L)).thenReturn(List.of(comment));

        List<CommentResponseDto> result = service.getCommentsByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }
}