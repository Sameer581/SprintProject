package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.entity.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

}