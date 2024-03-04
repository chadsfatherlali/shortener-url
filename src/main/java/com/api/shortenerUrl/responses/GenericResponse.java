package com.api.shortenerUrl.responses;

public class GenericResponse<T> extends Response {
	
	private T data;

	public T getData() {
		return data;
	}
	
	public void setData(T urlEntity) {
		this.data = urlEntity;
	}
	
}
