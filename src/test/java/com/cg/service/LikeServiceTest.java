package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.cg.dto.LikeDto;
import com.cg.entity.Like;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.repo.LikeRepo;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;
import com.cg.service.LikeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepo likeRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private LikeServiceImpl likeService;

    private Like like;
    private LikeDto likeDto;
    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        post = new Post();
        post.setPostId(1L);

        like = new Like();
        like.setLikeId(1L);
        like.setUser(user);
        like.setPost(post);
        like.setTimestamp(new Timestamp(System.currentTimeMillis()));

        likeDto = new LikeDto();
        likeDto.setUserId(1L);
        likeDto.setPostId(1L);
    }

    @Test
    void testToggleLike_WhenPostNotLiked() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(false);
        when(likeRepo.save(any(Like.class))).thenReturn(like);
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        LikeDto response = likeService.toggleLike(likeDto);

        assertEquals("Post liked successfully", response.getMessage());
        assertEquals(1, response.getTotalLikes());
        assertEquals(1L, response.getLikeId());
    }

    @Test
    void testToggleLike_WhenAlreadyLiked() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(true);
        when(likeRepo.findByUserUserIdAndPostPostId(1L, 1L)).thenReturn(like);
        when(likeRepo.countByPostPostId(1L)).thenReturn(0);

        LikeDto response = likeService.toggleLike(likeDto);

        assertEquals("Post unliked successfully", response.getMessage());
        assertEquals(0, response.getTotalLikes());
        verify(likeRepo, times(1)).delete(like);
    }

    @Test
    void testCountLikesByPost() {
        when(likeRepo.countByPostPostId(1L)).thenReturn(5);

        int count = likeService.countLikesByPost(1L);

        assertEquals(5, count);
    }

    @Test
    void testHasUserLiked_True() {
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(true);

        boolean result = likeService.hasUserLiked(1L, 1L);

        assertTrue(result);
    }

    @Test
    void testHasUserLiked_False() {
        when(likeRepo.existsByUserUserIdAndPostPostId(1L, 1L)).thenReturn(false);

        boolean result = likeService.hasUserLiked(1L, 1L);

        assertFalse(result);
    }

    @Test
    void testGetLikesByPost() {
        when(likeRepo.findByPostPostId(1L)).thenReturn(Collections.singletonList(like));
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        var result = likeService.getLikesByPost(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testGetLikesByUser() {
        when(likeRepo.findByUserUserId(1L)).thenReturn(Collections.singletonList(like));
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        var result = likeService.getLikesByUser(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getPostId());
    }

    @Test
    void testRemoveLike() {
        doNothing().when(likeRepo).deleteByUserUserIdAndPostPostId(1L, 1L);

        likeService.removeLike(1L, 1L);

        verify(likeRepo, times(1)).deleteByUserUserIdAndPostPostId(1L, 1L);
    }

    @Test
    void testGetRecentLikes() {
        when(likeRepo.findAllByOrderByTimestampDesc()).thenReturn(Arrays.asList(like));
        when(likeRepo.countByPostPostId(1L)).thenReturn(1);

        var result = likeService.getRecentLikes();

        assertEquals(1, result.size());
    }

    @Test
    void testGetTopLikedPosts() {
        when(likeRepo.findTopLikedPosts()).thenReturn(Arrays.asList(
                new Object[]{1L, 10L},
                new Object[]{2L, 5L}
        ));

        var result = likeService.getTopLikedPosts();

        assertEquals(2, result.size());
    }

    @Test
    void testCountLikesByUser() {
        when(likeRepo.countByUserUserId(1L)).thenReturn(4);

        int result = likeService.countLikesByUser(1L);

        assertEquals(4, result);
    }

    @Test
    void testClearLikesByPost() {
        doNothing().when(likeRepo).deleteByPostPostId(1L);

        likeService.clearLikesByPost(1L);

        verify(likeRepo, times(1)).deleteByPostPostId(1L);
    }
}