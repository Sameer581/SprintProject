package com.cg.service;

import java.util.List;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;

public interface CommentsService {

    CommentResponseDto addComment(CommentsDto dto);

    CommentResponseDto getComment(Long commentId);

    List<CommentResponseDto> getAllComments();

    List<CommentResponseDto> getCommentsByPostId(Long postId);

    List<CommentResponseDto> getCommentsByUserId(Long userId);
    
    CommentResponseDto updateComment(Long commentId, CommentUpdateDto dto);
}