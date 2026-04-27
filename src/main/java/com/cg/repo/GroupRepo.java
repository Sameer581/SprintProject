package com.cg.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Group;
import com.cg.entity.User;


@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {
	
	List<Group> findByAdmin(User admin);
	List<Group> findByAdminUserId(Integer adminId);
	Group findByGroupName(String groupName);
	
	

}
