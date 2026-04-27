package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Friendship;

public interface FriendshipRepo extends JpaRepository<Friendship, Integer>{

}
