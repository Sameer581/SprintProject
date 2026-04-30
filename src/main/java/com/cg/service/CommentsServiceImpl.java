package com.cg.service;

import java.time.LocalDateTime;
import java.util.List;
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
    public CommentResponseDto addComment(Long postId, CommentsDto dto) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotAvailableException("Post not found " + postId));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotAvailableException("User not found " + dto.getUserId()));

        Comments comment = new Comments();
        comment.setCommentText(dto.getCommentText());
        comment.setPost(post);
        comment.setUser(user);
        comment.setTimestamp(LocalDateTime.now());

        Comments saved = commentsRepo.save(comment);

        return mapToDto(saved);
    }

    @Override
    public CommentResponseDto getComment(Long commentId) {

        Comments comment = commentsRepo.findById(commentId)
                .orElseThrow(() -> new NotAvailableException("Comment not found " + commentId));

        return mapToDto(comment);
    }

    @Override
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {

        return commentsRepo.findByPost_PostId(postId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> getCommentsByUserId(Long userId) {

        return commentsRepo.findByUser_UserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentUpdateDto dto) {

        Comments comment = commentsRepo.findById(commentId)
                .orElseThrow(() -> new NotAvailableException("Comment not found " + commentId));

        comment.setCommentText(dto.getCommentText());

        return mapToDto(commentsRepo.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {

        if (!commentsRepo.existsById(commentId)) {
            throw new NotAvailableException("Comment not found " + commentId);
        }

        commentsRepo.deleteById(commentId);
    }

    private CommentResponseDto mapToDto(Comments comment) {

        CommentResponseDto dto = new CommentResponseDto();

        dto.setCommentId(comment.getCommentId());
        dto.setCommentText(comment.getCommentText());
        dto.setTimestamp(comment.getTimestamp());

        dto.setPostId(comment.getPost().getPostId());
        dto.setPostContent(comment.getPost().getContent());

        dto.setUserId(comment.getUser().getUserId());
        dto.setUsername(comment.getUser().getUsername());

        return dto;
    }

}