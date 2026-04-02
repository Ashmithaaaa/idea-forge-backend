package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String message;

    private boolean readStatus = false;

    public Notification(){}

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getMessage(){ return message; }
    public void setMessage(String message){ this.message = message; }

    public boolean isReadStatus(){ return readStatus; }
    public void setReadStatus(boolean readStatus){ this.readStatus = readStatus; }
}