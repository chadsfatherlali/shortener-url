package com.api.shortenerUrl.responses;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.api.shortenerUrl.entities.UrlEntity;

public class GenericResponsePageable<T> extends Response {
	
	private HttpStatus statusCode;
	private Page<UrlEntity> data;
	
	public Page<UrlEntity> getData() {
		return data;
	}

	public void setData(Page<UrlEntity> data) {
		this.data = data;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(HttpStatus ok) {
		this.statusCode = ok;
	}

}
