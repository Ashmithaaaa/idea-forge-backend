package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Activity;
import com.example.demo.repository.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repo;

    // ===============================
    // LOG ACTIVITY
    // ===============================
    public void logActivity(String username, String action, String target){

        Activity activity = new Activity();

        activity.setUsername(username);
        activity.setAction(action);
        activity.setTarget(target);
        activity.setCreatedAt(LocalDateTime.now());

        repo.save(activity);
    }

    // ===============================
    // GET ALL ACTIVITIES
    // ===============================
    public List<Activity> getActivities(){

        return repo.findAll();

    }

}