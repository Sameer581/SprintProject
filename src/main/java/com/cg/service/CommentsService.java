package com.cg.service;

import java.util.List;

import com.cg.dto.CommentsDto;
import com.cg.entity.Comments;

public interface CommentsService {

    public Long addComment(CommentsDto dto);

    public Comments getComment(Long commentID);

    public List<Comments> getAllComments();

    public List<Comments> getCommentsByPostID(Long postID);

    public List<Comments> getCommentsByUserID(Long userID);
}