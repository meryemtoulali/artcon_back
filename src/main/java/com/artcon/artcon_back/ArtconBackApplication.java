package com.artcon.artcon_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@PropertySource("file:src/main/resources/application-local.properties")
@RestController
public class ArtconBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtconBackApplication.class, args);
	}

	@GetMapping("")
	public String helloworld(){
		return "Hello world";
	}
}
