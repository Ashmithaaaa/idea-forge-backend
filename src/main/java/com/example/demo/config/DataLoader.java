package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Configuration
public class DataLoader {

	@Bean
	CommandLineRunner loadUser(UserRepository repo) {

	    return args -> {

	        if(repo.findByName("Guest User").isEmpty()){

	            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	            User user = new User();

	            user.setName("Guest User");
	            user.setEmail("guest@ideaforge.com");

	            user.setPassword(encoder.encode("123"));

	            user.setSkills("Innovation");

	            repo.save(user);

	            System.out.println("Guest User created");
	        }

	    };
	}
}