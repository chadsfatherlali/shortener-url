package com.api.shortenerUrl.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.api.shortenerUrl.entities.UrlEntity;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {

	Page<UrlEntity> findAll(Pageable paging);
	
	@Query("{ 'longUrl' : ?0 }")
	List<UrlEntity> findByUrl(String url);
	
	@Query("{ 'id' : ?0 }")
	UrlEntity findByUrlId(String urlId);

}
