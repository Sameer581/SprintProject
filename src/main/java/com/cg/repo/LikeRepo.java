package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    boolean existsByUserUserIDAndPostPostID(Long userId, Long postId);

    int countByPostPostID(Long postId);

    void deleteByUserUserIDAndPostPostID(Long userId, Long postId);
}