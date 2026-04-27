// LikeRepository.java
package com.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialmedia.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Check if user already liked the post
    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

    // Count total likes on a post
    long countByPostPostId(Long postId);

    // Unlike a post
    void deleteByUserUserIdAndPostPostId(Long userId, Long postId);
}