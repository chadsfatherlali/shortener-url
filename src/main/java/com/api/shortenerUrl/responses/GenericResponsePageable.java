package com.api.shortenerUrl.responses;

import org.springframework.data.domain.Page;

import com.api.shortenerUrl.entities.UrlEntity;

public class GenericResponsePageable<T> extends Response {
	
	private Page<UrlEntity> data;
	
	public Page<UrlEntity> getData() {
		return data;
	}

	public void setData(Page<UrlEntity> data) {
		this.data = data;
	}

}
