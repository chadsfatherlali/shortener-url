package com.api.shortenerUrl.responses;

import org.springframework.http.HttpStatus;

public class Response {
	
	private HttpStatus statusCode;
	private String errorMessage;


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(HttpStatus ok) {
		this.statusCode = ok;
	}
}
