package com.cg.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.CommentResponseDto;
import com.cg.dto.CommentUpdateDto;
import com.cg.dto.CommentsDto;
import com.cg.entity.Comments;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.CommentsRepo;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentResponseDto getComment(Long commentID) {
        Optional<Comments> optComment = commentsRepo.findById(commentID);

        if (optComment.isPresent()) {
            return mapToDto(optComment.get());
        }
        throw new NotAvailableException("Comment not found " + commentID);
    }

    @Override
    public List<CommentResponseDto> getAllComments() {
        return commentsRepo.findAllCommentsDto();
    }

    @Override
    public Long addComment(CommentsDto dto) {

        Optional<Post> optPost = postRepo.findById(dto.getPostID());
        if (optPost.isEmpty()) {
            throw new NotAvailableException("Post not found " + dto.getPostID());
        }

        Optional<User> optUser = userRepo.findById(dto.getUserID());
        if (optUser.isEmpty()) {
            throw new NotAvailableException("User not found " + dto.getUserID());
        }
        Comments comment = new Comments();
        comment.setCommentText(dto.getCommentText());
        comment.setPost(optPost.get());
        comment.setUser(optUser.get());
        comment.setTimestamp(LocalDateTime.now());

        Comments savedComment = commentsRepo.save(comment);

        return savedComment.getCommentId();
    }

    @Override
    public CommentResponseDto updateComment(Long commentID, CommentUpdateDto dto) {
        Optional<Comments> optComment = commentsRepo.findById(commentID);

        if (optComment.isEmpty()) {
            throw new NotAvailableException("Comment not found " + commentID);
        }
        Comments comment = optComment.get();
        comment.setCommentText(dto.getCommentText());
        Comments updated = commentsRepo.save(comment);

        return mapToDto(updated);
    }

    @Override
    public List<CommentResponseDto> getCommentsByPostID(Long postID) {

        List<Comments> comments = commentsRepo.findByPost_PostID(postID);

        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDto> getCommentsByUserID(Long userID) {

        List<Comments> comments = commentsRepo.findByUser_UserID(userID);

        return comments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    private CommentResponseDto mapToDto(Comments comment) {

        CommentResponseDto dto = new CommentResponseDto();

        dto.setCommentID(comment.getCommentId());
        dto.setCommentText(comment.getCommentText());
        dto.setTimestamp(comment.getTimestamp());

        dto.setPostID(comment.getPost().getPostId());
        dto.setPostContent(comment.getPost().getContent());

        dto.setUserID(comment.getUser().getUserID());
        dto.setUsername(comment.getUser().getUsername());

        return dto;
    }
}