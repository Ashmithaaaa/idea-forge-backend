package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ideaId;

    private String username;

    private String role;

    private LocalDateTime joinedAt;

    public TeamMember(){}

    public Long getId(){
        return id;
    }

    public Long getIdeaId(){
        return ideaId;
    }

    public void setIdeaId(Long ideaId){
        this.ideaId = ideaId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public LocalDateTime getJoinedAt(){
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt){
        this.joinedAt = joinedAt;
    }
}
