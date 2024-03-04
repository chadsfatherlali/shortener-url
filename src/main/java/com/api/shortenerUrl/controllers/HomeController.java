package com.api.shortenerUrl.controllers;

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

import com.api.shortenerUrl.services.HomeService;

@PropertySource("classpath:project.properties")
@RestController
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	HomeService service;
	
	@GetMapping("/")
    ResponseEntity<String> getHome() {
		return ResponseEntity.ok("Gangnam Style");
    }
	
	@GetMapping("/{urlId}")
    ResponseEntity<Void> getUrlRedirect(@PathVariable String urlId) {
		HttpHeaders headers = service.redirectHeaders(urlId);
		
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
	
}
