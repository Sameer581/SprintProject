package com.cg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Group;
import com.cg.entity.GroupMember;
import com.cg.entity.User;

@Repository
public interface GroupMemberRepo extends JpaRepository<GroupMember, Long> {

    
    List<GroupMember> findByGroup(Group group);

    
    List<GroupMember> findByUser(User user);

    
    Optional<GroupMember> findByGroupAndUser(Group group, User user);

    
    void deleteByGroupAndUser(Group group, User user);

    
    boolean existsByGroupAndUser(Group group, User user);
    
    long countByGroup(Group group);
}