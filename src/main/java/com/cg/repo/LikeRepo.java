package com.cg.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

    int countByPostPostId(Long postId);

    int countByUserUserId(Long userId);

    Like findByUserUserIdAndPostPostId(Long userId, Long postId);

    List<Like> findByPostPostId(Long postId);

    List<Like> findByUserUserId(Long userId);

    void deleteByUserUserIdAndPostPostId(Long userId, Long postId);

    void deleteByPostPostId(Long postId);

    List<Like> findAllByOrderByTimestampDesc();

    List<Like> findAllByOrderByTimestampDesc(Pageable pageable);

    @Query("SELECT l.post.postId, COUNT(l) FROM Like l GROUP BY l.post.postId ORDER BY COUNT(l) DESC")
    List<Object[]> findTopLikedPosts();
}