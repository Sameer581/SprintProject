package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
