package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {

<<<<<<< HEAD
    boolean existsByUserUserIDAndPostPostID(Long userId, Long postId);

    int countByPostPostID(Long postId);

    void deleteByUserUserIDAndPostPostID(Long userId, Long postId);
=======
    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

    int countByPostPostId(Long postId);

    Like findByUserUserIdAndPostPostId(Long userId, Long postId);

    List<Like> findByPostPostId(Long postId);

    List<Like> findByUserUserId(Long userId);
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3
}