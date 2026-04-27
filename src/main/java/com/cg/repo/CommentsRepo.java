package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

}
