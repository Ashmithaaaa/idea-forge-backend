package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ideaId;

    private String username;

    @Column(length = 1000)
    private String commentText;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment(){}

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public Long getIdeaId(){return ideaId;}
    public void setIdeaId(Long ideaId){this.ideaId=ideaId;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}

    public String getCommentText(){return commentText;}
    public void setCommentText(String commentText){this.commentText=commentText;}

    public LocalDateTime getCreatedAt(){return createdAt;}
}