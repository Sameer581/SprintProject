package com.cg.service;

import java.util.List;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;

public interface CommentsService {

    public Long addComment(CommentsDto dto);

    public CommentResponseDto getComment(Long commentID);

    public List<CommentResponseDto> getAllComments();

    public List<CommentResponseDto> getCommentsByPostID(Long postID);

    public List<CommentResponseDto> getCommentsByUserID(Long userID);
    
   public CommentResponseDto updateComment(Long commentID, CommentUpdateDto dto);
}