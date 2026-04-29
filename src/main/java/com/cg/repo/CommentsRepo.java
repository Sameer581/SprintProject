package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Comments;
public interface CommentsRepo extends JpaRepository<Comments, Long> {

    List<Comments> findByPost_PostID(Long postID);

    List<Comments> findByUser_UserID(Long userID);
}