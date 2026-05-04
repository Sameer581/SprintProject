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

    Optional<GroupMember> findByGroupAndUserAndStatus(Group group, User user, String status);

    List<GroupMember> findByGroupAndStatus(Group group, String status);

    List<GroupMember> findByGroupAndRole(Group group, String role);

    List<GroupMember> findByGroupAndRoleAndStatus(Group group, String role, String status);

    List<GroupMember> findByUserAndStatus(User user, String status);

    boolean existsByGroupAndUser(Group group, User user);

    boolean existsByGroupAndUserAndStatus(Group group, User user, String status);

    long countByGroup(Group group);

    long countByGroupAndStatus(Group group, String status);

    void deleteByGroupAndUser(Group group, User user);
}