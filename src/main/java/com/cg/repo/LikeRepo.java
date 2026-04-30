package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {

    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

    int countByPostPostId(Long postId);

    Like findByUserUserIdAndPostPostId(Long userId, Long postId);

    List<Like> findByPostPostId(Long postId);

    List<Like> findByUserUserId(Long userId);
}