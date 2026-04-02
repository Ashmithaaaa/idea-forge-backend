package com.example.demo.controller;

import com.example.demo.model.TeamMember;
import com.example.demo.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@CrossOrigin("*")
public class TeamController {

    @Autowired
    private TeamRepository teamRepo;


    // Get team for idea
    @GetMapping("/{ideaId}")
    public List<TeamMember> getTeam(@PathVariable Long ideaId){

        return teamRepo.findByIdeaId(ideaId);

    }

}
