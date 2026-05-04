package com.cg.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cg.dto.LikeDto;
import com.cg.exception.ValidationException;
import com.cg.service.LikeService;
import com.cg.web.LikeController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    private LikeDto likeDto;

    @BeforeEach
    void setUp() {
        likeDto = new LikeDto();
        likeDto.setLikeId(1L);
        likeDto.setUserId(1L);
        likeDto.setPostId(1L);
        likeDto.setTimestamp(new Timestamp(System.currentTimeMillis()));
        likeDto.setMessage("Post liked successfully");
        likeDto.setTotalLikes(1);
    }

    @Test
    void testToggleLike_Success() {
        BindingResult br = new BeanPropertyBindingResult(likeDto, "likeDto");

        when(likeService.toggleLike(any(LikeDto.class))).thenReturn(likeDto);

        LikeDto response = likeController.toggleLike(likeDto, br);

        assertEquals(1L, response.getLikeId());
        assertEquals("Post liked successfully", response.getMessage());
        assertEquals(1, response.getTotalLikes());
    }

    @Test
    void testToggleLike_ValidationException() {
        BindingResult br = new BeanPropertyBindingResult(likeDto, "likeDto");
        br.addError(new FieldError("likeDto", "userId", "User ID is required"));

        ValidationException ex = org.junit.jupiter.api.Assertions.assertThrows(
                ValidationException.class,
                () -> likeController.toggleLike(likeDto, br)
        );

        assertEquals("User ID is required", ex.getErrors().get(0).getDefaultMessage());
    }

    @Test
    void testCountLikesByPost() {
        when(likeService.countLikesByPost(1L)).thenReturn(5);

        ResponseEntity<Integer> response = likeController.countLikesByPost(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody());
    }

    @Test
    void testHasUserLiked() {
        when(likeService.hasUserLiked(1L, 1L)).thenReturn(true);

        ResponseEntity<Boolean> response = likeController.hasUserLiked(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testGetLikesByPost() {
        List<LikeDto> likes = Collections.singletonList(likeDto);

        when(likeService.getLikesByPost(1L)).thenReturn(likes);

        ResponseEntity<List<LikeDto>> response = likeController.getLikesByPost(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetLikesByUser() {
        List<LikeDto> likes = Collections.singletonList(likeDto);

        when(likeService.getLikesByUser(1L)).thenReturn(likes);

        ResponseEntity<List<LikeDto>> response = likeController.getLikesByUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testRemoveLike() {
        doNothing().when(likeService).removeLike(1L, 1L);

        ResponseEntity<String> response = likeController.removeLike(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Like removed successfully", response.getBody());
    }

    @Test
    void testGetRecentLikes() {
        List<LikeDto> likes = Arrays.asList(likeDto);

        when(likeService.getRecentLikes()).thenReturn(likes);

        ResponseEntity<List<LikeDto>> response = likeController.getRecentLikes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetRecentLikesByCount() {
        List<LikeDto> likes = Arrays.asList(likeDto);

        when(likeService.getRecentLikesByCount(5)).thenReturn(likes);

        ResponseEntity<List<LikeDto>> response = likeController.getRecentLikesByCount(5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetTopLikedPosts() {
        List<Object[]> topPosts = Arrays.asList(
                new Object[]{1L, 10L},
                new Object[]{2L, 7L}
        );

        when(likeService.getTopLikedPosts()).thenReturn(topPosts);

        ResponseEntity<List<Object[]>> response = likeController.getTopLikedPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testCountLikesByUser() {
        when(likeService.countLikesByUser(1L)).thenReturn(8);

        ResponseEntity<Integer> response = likeController.countLikesByUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(8, response.getBody());
    }

    @Test
    void testClearLikesByPost() {
        doNothing().when(likeService).clearLikesByPost(1L);

        ResponseEntity<String> response = likeController.clearLikesByPost(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All likes removed from post", response.getBody());
    }

    @Test
    void testHandleValidationException() {
        List<FieldError> fieldErrors = List.of(
                new FieldError("likeDto", "userId", "User ID is required"),
                new FieldError("likeDto", "postId", "Post ID is required")
        );

        ValidationException ex = new ValidationException(fieldErrors);

        ResponseEntity<Map<String, String>> response = likeController.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User ID is required", response.getBody().get("userId"));
        assertEquals("Post ID is required", response.getBody().get("postId"));
    }
}