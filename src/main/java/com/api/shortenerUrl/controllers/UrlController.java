package com.api.shortenerUrl.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.responses.GenericResponse;
import com.api.shortenerUrl.responses.GenericResponsePageable;
import com.api.shortenerUrl.services.UrlService;

import jakarta.validation.Valid;

@PropertySource("classpath:project.properties")
@RestController
public class UrlController {
	
    Logger logger = LoggerFactory.getLogger(UrlController.class);
    
    @Autowired
	UrlService service;
	
	@GetMapping("/url")
    ResponseEntity<GenericResponsePageable<List<UrlEntity>>> listUrls(
    		@RequestParam(required = false) String search,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size
    ) {
		GenericResponsePageable<List<UrlEntity>> response = service.listUrls(search, page, size);
		
		return ResponseEntity.ok(response);
    }
	
	@PostMapping("/url")
    ResponseEntity<GenericResponse<UrlEntity>> addUrl(@Valid @RequestBody UrlEntity urlBody) {
		GenericResponse<UrlEntity> response = service.addUrl(urlBody);
		
		return ResponseEntity.ok(response);
    }
	
}
