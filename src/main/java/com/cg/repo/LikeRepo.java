package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    // Check if user already liked the post
    boolean existsByUserUserIDAndPostPostID(Long userId, Long postId);

    // Count total likes on a post
    int countByPostPostID(Long postId);

    // Unlike a post
    void deleteByUserUserIDAndPostPostID(Long userId, Long postId);
}