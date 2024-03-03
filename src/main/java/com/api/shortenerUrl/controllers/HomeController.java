package com.api.shortenerUrl.controllers;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.repositories.UrlRepository;

@PropertySource("classpath:project.properties")
@RestController
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UrlRepository repository;
	
	@GetMapping("/{urlId}")
    ResponseEntity<Void> getUrlRedirect(@PathVariable String urlId) {
		UrlEntity urlObject = repository.findByUrlId(urlId);
		
		logger.info("INFO::URLOBJECTLONGURL" + urlObject.getLongUrl());
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(URI.create(urlObject.getLongUrl()));
		
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
	
}
