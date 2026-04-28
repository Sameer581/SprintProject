package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {

    boolean existsByUserUserIDAndPostPostID(Long userId, Long postId);

    int countByPostPostID(Long postId);

    Like findByUserUserIDAndPostPostID(Long userId, Long postId);

    List<Like> findByPostPostID(Long postId);

    List<Like> findByUserUserID(Long userId);
}