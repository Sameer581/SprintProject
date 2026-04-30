package com.cg.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Group;
import com.cg.entity.User;
import java.util.Optional;


@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
	
	List<Group> findByAdmin(User admin);
	List<Group> findByAdminUserId(Long adminId);
	Optional<Group> findByGroupName(String groupName);
	
	

}
