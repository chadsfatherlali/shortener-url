package com.api.shortenerUrl.responses;

import org.springframework.http.HttpStatus;

public class GenericResponse<T> extends Response {
	
	private HttpStatus statusCode;
	private T data;

	public T getData() {
		return data;
	}
	
	public void setData(T urlEntity) {
		this.data = urlEntity;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(HttpStatus ok) {
		this.statusCode = ok;
	}
	
}
