package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

    int countByPostPostId(Long postId);

    void deleteByUserUserIdAndPostPostId(Long userId, Long postId);
}