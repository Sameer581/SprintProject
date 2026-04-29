package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.dto.CommentResponseDto;
import com.cg.entity.Comments;
public interface CommentsRepo extends JpaRepository<Comments, Long> {
	@Query("""
			SELECT new com.cg.dto.CommentResponseDto(
			    c.commentId,
			    c.commentText,
			    c.timestamp,
			    p.postId,
			    p.content,
			    u.userId,
			    u.username
			)
			FROM Comments c
			JOIN c.post p
			JOIN c.user u
			""")
			List<CommentResponseDto> findAllCommentsDto();
	
    List<Comments> findByUser_UserId(Long userId);

	List<Comments> findByPost_PostId(Long postId);
}