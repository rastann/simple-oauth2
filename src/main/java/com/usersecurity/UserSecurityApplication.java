package com.usersecurity;

import java.util.Arrays;

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
public class UserSecurityApplication {

//	@Bean
//	public CommandLineRunner setupDefaultUser(UserService service) {
//		return args -> {
//			service.save(new User("user", "user", Arrays.asList(new Role("ROLE_CLIENT")),true));
//			service.save(new User("admin", "admin", Arrays.asList(new Role("ROLE_ADMIN")),true));
//			service.save(new User("ion", "zapada", Arrays.asList(new Role("ROLE_TRUSTED_CLIENT")),true));
//		};
//	}

	@Bean
	public CommandLineRunner setupDefaultUser(UserService service) {
		return args -> {
			service.save(new User("user", "user", Arrays.asList(new Role("CLIENT")),true));
			service.save(new User("admin", "admin", Arrays.asList(new Role("ADMIN")),true));
			service.save(new User("ion", "zapada", Arrays.asList(new Role("TRUSTED")),true));
		};
	}

	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserSecurityApplication.class, args);
	}

}
