package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.CommentsDto;
import com.cg.entity.Comments;
import com.cg.entity.Post;
import com.cg.entity.User;
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

    public Comments getComment(int commentID) {

        Optional<Comments> opComment = commentsRepo.findById(commentID);

        if (opComment.isPresent()) {
            return opComment.get();
        }

        throw new NotAvaliableException("Comment not found " + commentID);
    }

    
    public List<Comments> getAllComments() {

        return commentsRepo.findAll();
    }
    
    public Integer addComment(CommentsDto dto) {

        Optional<Post> optPost = postRepo.findById(dto.getPostID());

        if (optPost.isEmpty()) {
            throw new NotAvaliableException("Post not found " + dto.getPostID());
        }

        Optional<User> optUser = userRepo.findById(dto.getUserID());

        if (optUser.isEmpty()) {
            throw new NotAvaliableException("User not found " + dto.getUserID());
        }

        Post post = optPost.get();
        User user = optUser.get();

        Comments comment = new Comments();

        comment.setCommentText(dto.getCommentText());
        comment.setTimestamp(dto.getTimestamp());
        comment.setPost(post);
        comment.setUser(user);

        Comments savedComment = commentsRepo.save(comment);

        return savedComment.getCommentID();
    }
    
    @Override
    public List<Comments> getCommentsByPostID(int postID) {

        return commentsRepo.findByPost_PostID((long) postID);
    }

    @Override
    public List<Comments> getCommentsByUserID(int userID) {

        return commentsRepo.findByUser_UserID(userID);
    }
}