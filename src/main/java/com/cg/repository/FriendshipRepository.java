package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer>{

}
