package com.artcon.artcon_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:src/main/resources/application-local.properties")
public class ArtconBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtconBackApplication.class, args);
	}

}
