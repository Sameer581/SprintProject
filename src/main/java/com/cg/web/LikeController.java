package com.cg.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.LikeDto;
import com.cg.exception.ValidationException;
import com.cg.service.LikeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    @ResponseStatus(HttpStatus.OK)
    public LikeDto toggleLike(@Valid @RequestBody LikeDto likeDto, BindingResult br) {
        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }
        return likeService.toggleLike(likeDto);
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> countLikesByPost(@PathVariable Long postId) {
        return new ResponseEntity<>(likeService.countLikesByPost(postId), HttpStatus.OK);
    }

    @GetMapping("/check/{userId}/{postId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long userId, @PathVariable Long postId) {
        return new ResponseEntity<>(likeService.hasUserLiked(userId, postId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getLikesByPost(@PathVariable Long postId) {
        return new ResponseEntity<>(likeService.getLikesByPost(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDto>> getLikesByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(likeService.getLikesByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}/{postId}")
    public ResponseEntity<String> removeLike(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.removeLike(userId, postId);
        return new ResponseEntity<>("Like removed successfully", HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<LikeDto>> getRecentLikes() {
        return new ResponseEntity<>(likeService.getRecentLikes(), HttpStatus.OK);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<LikeDto>> getRecentLikesByCount(@PathVariable int count) {
        return new ResponseEntity<>(likeService.getRecentLikesByCount(count), HttpStatus.OK);
    }

    @GetMapping("/top-posts")
    public ResponseEntity<List<Object[]>> getTopLikedPosts() {
        return new ResponseEntity<>(likeService.getTopLikedPosts(), HttpStatus.OK);
    }

    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Integer> countLikesByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(likeService.countLikesByUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/clear/post/{postId}")
    public ResponseEntity<String> clearLikesByPost(@PathVariable Long postId) {
        likeService.clearLikesByPost(postId);
        return new ResponseEntity<>("All likes removed from post", HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}