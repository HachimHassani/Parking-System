package com.platform.parkingsystem;

import com.platform.parkingsystem.api.auth.AuthenticationService;
import com.platform.parkingsystem.api.auth.RegisterRequest;
import com.platform.parkingsystem.api.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.platform.parkingsystem.api.user.Role.MANAGER;

@SpringBootApplication
public class ParkingSystemApplication {

	public static void main(String[] args) {
		System.out.println("the user is ....");
		SpringApplication.run(ParkingSystemApplication.class, args);
	}


}
