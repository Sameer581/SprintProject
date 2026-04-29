package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.LikeDto;
import com.cg.entity.Like;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.repo.LikeRepo;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepo likeRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private LikeServiceImpl service;

    private User user;
    private Post post;
    private Like like;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("Shruti");

        post = new Post();
        post.setPostId(1L);
        post.setContent("Test Post");

        like = new Like();
        like.setLikeId(1L);
        like.setUser(user);
        like.setPost(post);
        like.setTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testToggleLike_newLike() {
        LikeDto dto = new LikeDto();
        dto.setUserId(1L);
        dto.setPostId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(false);
        when(likeRepo.save(any(Like.class))).thenReturn(like);
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        LikeDto result = service.toggleLike(dto);

        assertEquals("Post liked successfully", result.getMessage());
        assertEquals(1, result.getTotalLikes());
        verify(likeRepo, times(1)).save(any(Like.class));
    }

    @Test
    void testToggleLike_unlike() {
        LikeDto dto = new LikeDto();
        dto.setUserId(1L);
        dto.setPostId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(true);
        when(likeRepo.findByUserUserIdAndPostPostId(1L, 1L)).thenReturn(like);
        when(likeRepo.countByPostPostId(1L)).thenReturn(0);

        LikeDto result = service.toggleLike(dto);

        assertEquals("Post unliked successfully", result.getMessage());
        assertEquals(0, result.getTotalLikes());
        verify(likeRepo, times(1)).delete(like);
    }

    @Test
    void testCountLikesByPost() {
        when(likeRepo.countByPostPostId(1L)).thenReturn(5);

        int result = service.countLikesByPost(1L);

        assertEquals(5, result);
    }

    @Test
    void testHasUserLiked_true() {
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(true);

        boolean result = service.hasUserLiked(1L, 1L);

        assertTrue(result);
    }

    @Test
    void testHasUserLiked_false() {
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(false);

        boolean result = service.hasUserLiked(1L, 1L);

        assertFalse(result);
    }

    @Test
    void testGetLikesByPost() {
        when(likeRepo.findByPostPostId(1L)).thenReturn(List.of(like));
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        List<LikeDto> result = service.getLikesByPost(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        assertEquals("Like fetched successfully", result.get(0).getMessage());
    }

    @Test
    void testGetLikesByUser() {
        when(likeRepo.findByUserUserId(1L)).thenReturn(List.of(like));
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        List<LikeDto> result = service.getLikesByUser(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getPostId());
        assertEquals("Like fetched successfully", result.get(0).getMessage());
    }
}