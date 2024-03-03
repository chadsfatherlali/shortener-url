package com.api.shortenerUrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:database.properties")
@Configuration
public class AppConfig {
	
	@Value("${database.url}")
	private String url;
	
	@Bean com.mongodb.client.MongoClient mongoClient() {
		return com.mongodb.client.MongoClients.create(this.url);
	}
	
}
