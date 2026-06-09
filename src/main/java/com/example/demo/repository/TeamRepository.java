package com.example.demo.repository;

import com.example.demo.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamMember,Long>{

    List<TeamMember> findByIdeaId(Long ideaId);

}
