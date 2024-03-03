package com.api.shortenerUrl.entities;

import java.time.Instant;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;


@Document
public class UrlEntity {
	
	@Id 
	private String id;
	
	@TextIndexed
    @NotBlank(message = "longUrl parameter is mandatory ")
	@URL
	public String longUrl;
	
	public String shortUrl;
	
	@CreatedDate 
	public Instant createdDate = Instant.now();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLongUrl() {
		return longUrl;
	}
	
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
	public String getShortUrl() {
		return shortUrl;
	}
	
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	public Instant getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

}
