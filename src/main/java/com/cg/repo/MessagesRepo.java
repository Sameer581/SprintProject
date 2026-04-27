package com.cg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.entity.Messages;

public interface MessagesRepo extends JpaRepository<Messages, Long> {

}