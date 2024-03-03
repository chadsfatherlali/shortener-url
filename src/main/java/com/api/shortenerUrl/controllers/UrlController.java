package com.api.shortenerUrl.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.repositories.UrlRepository;
import com.api.shortenerUrl.repositories.UrlRepositoryTextSearch;
import com.api.shortenerUrl.responses.GenericResponse;
import com.api.shortenerUrl.responses.GenericResponsePageable;
import com.api.shortenerUrl.utils.Id;

import jakarta.validation.Valid;

@PropertySource("classpath:project.properties")
@RestController
public class UrlController {
	
    Logger logger = LoggerFactory.getLogger(UrlController.class);
	
	@Autowired
	private UrlRepository repository;
	
	@Autowired
	private UrlRepositoryTextSearch repositorySearch;
	
	@Value("${project.domain}")
	private String domain;
	
	@GetMapping("/url")
    ResponseEntity<GenericResponsePageable<List<UrlEntity>>> listUrls(
    		@RequestParam(required = false) String search,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size
    ) {
		Pageable paging = PageRequest.of(page, size);
		GenericResponsePageable<List<UrlEntity>> response = new GenericResponsePageable<List<UrlEntity>>();
		
		response.setStatusCode(HttpStatus.OK);
		
		if (search != null) {
			logger.info("INFO::SEARCH " + search);
			
			TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(search);
			
			response.setData(repositorySearch.findAllBy(criteria, paging));

			return ResponseEntity.ok(response);
		}

		response.setData(repository.findAll(paging));
		
		return ResponseEntity.ok(response);
    }
	
	@PostMapping("/url")
    ResponseEntity<GenericResponse<UrlEntity>> addUrl(@Valid @RequestBody UrlEntity urlBody) {
		GenericResponse<UrlEntity> response = new GenericResponse<UrlEntity>();
		List<UrlEntity> findUrls = repository.findByUrl(urlBody.longUrl);
		
		response.setStatusCode(HttpStatus.OK);
		
		if (!findUrls.isEmpty()) {
			logger.info("INFO::URLSLIST " + findUrls.toString());
			
			response.setData(findUrls.get(0));
			
			return ResponseEntity.ok(response);
		}
		
		logger.info("INFO::NOEXISTURL " + urlBody.toString());
		
		String urlId = Id.generate();
		UrlEntity createUrl = new UrlEntity();
		
		createUrl.setId(urlId);
		createUrl.setLongUrl(urlBody.longUrl);
		createUrl.setShortUrl(this.domain + urlId);
		
		response.setData(repository.save(createUrl));
		
		return ResponseEntity.ok(response);
    }
	
}
