package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "group_members",
    uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "user_id"})
)
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
    @Column(name = "role")
    private String role;   

    public GroupMember() {
    }

    public GroupMember(Group group, User user, String role) {
        this.group = group;
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}