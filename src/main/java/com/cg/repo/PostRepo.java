package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long>{

}
