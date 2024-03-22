package ru.pinchuk.fileExchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import ru.pinchuk.fileExchange.service.RoleService;
import ru.pinchuk.fileExchange.service.UserService;

@SpringBootApplication
public class FileExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExchangeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleService roles) {
		return args -> {
//			if (roles.getRoleByName("USER") != null) {
//				roles.addRole("USER");
//			}
//			if (roles.getRoleByName("ADMIN") != null) {
//				roles.addRole("ADMIN");
//			}
		};
	}

}
