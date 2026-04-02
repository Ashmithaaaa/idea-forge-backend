package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Activity;
import com.example.demo.service.ActivityService;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin("*")
public class ActivityController {

    @Autowired
    private ActivityService service;

    @GetMapping
    public List<Activity> getActivities(){

        return service.getActivities();
    }
}