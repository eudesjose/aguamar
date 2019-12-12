package com.distribuidora.aguamar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AguamarApplication {

	public static void main(String[] args) {
		SpringApplication.run(AguamarApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("admin"));
	}

}
