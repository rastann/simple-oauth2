package com.usersecurity;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.usersecurity.entities.Role;
import com.usersecurity.entities.User;
import com.usersecurity.services.UserService;

@SpringBootApplication
public class Application {

	@Autowired
	private UserService service;

	@PostConstruct
	public void h2Prep() {
		service.save(new User("user", "user", Arrays.asList(new Role("USER"))));
		service.save(new User("admin", "admin", Arrays.asList(new Role("ADMIN"))));
		service.save(new User("ion", "zapada", Arrays.asList(new Role("ADMIN"))));
	}



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
