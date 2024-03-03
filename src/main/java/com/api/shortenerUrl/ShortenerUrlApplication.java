package com.api.shortenerUrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ShortenerUrlApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShortenerUrlApplication.class, args);
	}

}
