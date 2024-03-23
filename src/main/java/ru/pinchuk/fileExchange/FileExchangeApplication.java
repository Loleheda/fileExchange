package ru.pinchuk.fileExchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ru.pinchuk.fileExchange.service.RoleService;
import ru.pinchuk.fileExchange.service.UserService;

@SpringBootApplication
public class FileExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExchangeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleService roleService, UserService userService) {
		return args -> {
			if (roleService.getRoleByName("USER") == null) {
				roleService.addRole("USER");
			}
			if (roleService.getRoleByName("ADMIN") == null) {
				roleService.addRole("ADMIN");
			}
			if (userService.getByLogin("admin") == null) {
				userService.addUser("admin", "admin", "admin@mail.com");
			}
			if (roleService.getRoleByName("ADMIN") == null) {
				roleService.addRole("ADMIN");
			}
		};
	}

}
