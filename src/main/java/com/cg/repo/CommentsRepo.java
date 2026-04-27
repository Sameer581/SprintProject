package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Long>{
	public List<Comments> findCommentsByPostID(Long PostID);
	
	public List<Comments> findCommentsByUserId(Long userID);

}
