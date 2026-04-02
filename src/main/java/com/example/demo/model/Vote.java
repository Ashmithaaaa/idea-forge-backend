package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ideaId;

    private String username;

    public Vote(){}

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public Long getIdeaId(){return ideaId;}
    public void setIdeaId(Long ideaId){this.ideaId=ideaId;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
}