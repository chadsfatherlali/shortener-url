package com.api.shortenerUrl.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.shortenerUrl.entities.UrlEntity;
import com.api.shortenerUrl.repositories.UrlRepository;
import com.api.shortenerUrl.repositories.UrlRepositoryTextSearch;
import com.api.shortenerUrl.responses.GenericResponse;
import com.api.shortenerUrl.responses.GenericResponsePageable;
import com.api.shortenerUrl.utils.Id;

@Service
public class UrlService {
	
	Logger logger = LoggerFactory.getLogger(UrlService.class);

	@Value("${project.domain}")
	private String domain;
	
	@Autowired
	private UrlRepository repository;
	
	@Autowired
	private UrlRepositoryTextSearch repositorySearch;
	
	/**
	 * Search for urls
	 * @param search
	 * @param page
	 * @param size
	 * @return
	 */
	public GenericResponsePageable<List<UrlEntity>> listUrls(String search, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		GenericResponsePageable<List<UrlEntity>> response = new GenericResponsePageable<List<UrlEntity>>();
		
		response.setStatusCode(HttpStatus.OK);
		
		if (search != null) {
			logger.info("INFO::SEARCH " + search);
			
			TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(search);
			
			response.setData(repositorySearch.findAllBy(criteria, paging));

			return response;
		}

		response.setData(repository.findAll(paging));
		
		return response;
	}
	
	/**
	 * Add new url
	 * @param urlBody
	 * @return
	 */
	public GenericResponse<UrlEntity> addUrl(UrlEntity urlBody) {
		GenericResponse<UrlEntity> response = new GenericResponse<UrlEntity>();
		List<UrlEntity> findUrls = repository.findByUrl(urlBody.longUrl);
		
		response.setStatusCode(HttpStatus.OK);
		
		if (!findUrls.isEmpty()) {
			logger.info("INFO::URLSLIST " + findUrls.toString());
			
			response.setData(findUrls.get(0));
			
			return response;
		}
		
		logger.info("INFO::NOEXISTURL " + urlBody.toString());
		
		String urlId = Id.generate();
		UrlEntity createUrl = new UrlEntity();
		
		createUrl.setId(urlId);
		createUrl.setLongUrl(urlBody.longUrl);
		createUrl.setShortUrl(this.domain + urlId);
		
		response.setData(repository.save(createUrl));
		
		return response;
	}
	
}
