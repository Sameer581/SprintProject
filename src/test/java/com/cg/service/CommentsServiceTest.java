package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.cg.service.CommentsService;
import com.cg.service.CommentsServiceImpl;
@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {

    @Mock
    private CommentsRepo commentsRepo;

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CommentsService service = new CommentsServiceImpl();

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

        CommentsDto dto = new CommentsDto(1L, 1L, "Hello");

        Mockito.when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(commentsRepo.save(any(Comments.class))).thenReturn(comment);

        CommentResponseDto result = service.addComment(dto);

        assertEquals(1L, result);
        verify(commentsRepo, times(1)).save(any());
    }

    @Test
    void testAddComment_postNotFound() {

        CommentsDto dto = new CommentsDto(1L, 1L, "Hello");

        when(postRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            service.addComment(dto);
        });
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

        assertThrows(NotAvailableException.class, () -> {
            service.getComment(1L);
        });
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

        assertThrows(NotAvailableException.class, () -> {
            service.updateComment(1L, dto);
        });
    }
}