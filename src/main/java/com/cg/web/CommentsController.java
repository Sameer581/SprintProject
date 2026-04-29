package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;
import com.cg.exception.ValidationException;
import com.cg.service.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentsDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }
        return commentsService.addComment(postId, dto);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getCommentsByPost(@PathVariable Long postId) {
        return commentsService.getCommentsByPostId(postId);
    }

    @GetMapping("/comments/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentsService.getComment(id);
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateDto dto,
            BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        return commentsService.updateComment(id, dto);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
    }
    @GetMapping("/users/{userId}/comments")
    public List<CommentResponseDto> getCommentsByUser(@PathVariable Long userId) {
        return commentsService.getCommentsByUserId(userId);
    }
}