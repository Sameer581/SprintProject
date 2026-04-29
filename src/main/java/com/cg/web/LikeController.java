package com.cg.web;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
        int totalLikes = likeService.countLikesByPost(postId);
        return new ResponseEntity<Integer>(totalLikes, HttpStatus.OK);
    }

    @GetMapping("/check/{userId}/{postId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long userId, @PathVariable Long postId) {
        boolean liked = likeService.hasUserLiked(userId, postId);
        return new ResponseEntity<Boolean>(liked, HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getLikesByPost(@PathVariable Long postId) {
        return new ResponseEntity<>(likeService.getLikesByPost(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDto>> getLikesByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(likeService.getLikesByUser(userId), HttpStatus.OK);
    }

}