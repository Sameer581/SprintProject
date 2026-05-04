package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_members")
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

    @Column(nullable = false)
    private String role = "MEMBER"; 

    @Column(nullable = false)
    private String status = "ACTIVE"; 

    public GroupMember() {}

    public GroupMember(Group group, User user, String role) {
        this.group = group;
        this.user = user;
        this.role = role;
        this.status = "ACTIVE";
    }

    public GroupMember(Group group, User user, String role, String status) {
        this.group = group;
        this.user = user;
        this.role = role;
        this.status = status;
    }

    public Long getId() { 
        return id; 
        }
    public void setId(Long id) { 
        this.id = id; 
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

    public String getStatus() { 
        return status; 
        }
    public void setStatus(String status) { 
        this.status = status; 
        }
}