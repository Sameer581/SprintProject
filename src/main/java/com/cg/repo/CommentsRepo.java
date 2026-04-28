package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.dto.CommentResponseDto;
import com.cg.entity.Comments;
public interface CommentsRepo extends JpaRepository<Comments, Long> {
	@Query("""
			SELECT new com.cg.dto.CommentResponseDto(
			    c.commentID,
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

    List<Comments> findByPost_PostID(Long postID);

    List<Comments> findByUser_UserID(Long userID);
}