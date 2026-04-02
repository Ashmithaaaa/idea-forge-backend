package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>{

}