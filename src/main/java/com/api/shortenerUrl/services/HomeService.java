package com.api.shortenerUrl.services;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.repositories.UrlRepository;

@Service
public class HomeService {
	
	Logger logger = LoggerFactory.getLogger(HomeService.class);

	@Autowired
	private UrlRepository repository;
	
	/**
	 * Return redirect headers
	 * @param urlId
	 * @return
	 */
	public HttpHeaders redirectHeaders(String urlId) {
		UrlEntity urlObject = repository.findByUrlId(urlId);
		
		logger.info("INFO::URLOBJECTLONGURL" + urlObject.getLongUrl());
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(URI.create(urlObject.getLongUrl()));
		
		return headers;
	}
	
}
