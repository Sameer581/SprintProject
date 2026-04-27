package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Comments getComment(Long commentID) {

        Optional<Comments> optComment = commentsRepo.findById(commentID);

        if (optComment.isPresent()) {
            return optComment.get();
        }

        throw new NotAvailableException("Comment not found " + commentID);
    }

    @Override
    public List<Comments> getAllComments() {
        return commentsRepo.findAll();
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
        comment.setTimestamp(dto.getTimestamp());
        comment.setPost(optPost.get());
        comment.setUser(optUser.get());

        Comments savedComment = commentsRepo.save(comment);

        return savedComment.getCommentID();
    }

    @Override
    public List<Comments> getCommentsByPostID(Long postID) {
        return commentsRepo.findCommentsByPostID(postID);
    }

    @Override
    public List<Comments> getCommentsByUserID(Long userID) {
        return commentsRepo.findCommentsByUserId(userID);
        }
}