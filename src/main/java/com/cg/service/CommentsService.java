package com.cg.service;

import java.util.List;

import com.cg.dto.CommentsDto;
import com.cg.entity.Comments;

public interface CommentsService {

    public Integer addComment(CommentsDto dto);

    public Comments getComment(int commentID);

    public List<Comments> getAllComments();

    public List<Comments> getCommentsByPostID(int postID);

    public List<Comments> getCommentsByUserID(int userID);
}