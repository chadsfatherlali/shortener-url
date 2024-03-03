package com.api.shortenerUrl.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.api.shortenerUrl.responses.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody GenericResponse<?>
	handleParamBadRequest(HttpServletRequest req, Exception ex) {
		GenericResponse<?> response = new GenericResponse();
		
		response.setErrorMessage(ex.getLocalizedMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		
		return response;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody GenericResponse<?>
	handleBodyBadRequest(HttpServletRequest req, Exception ex) {
		GenericResponse<?> response = new GenericResponse();
		
		response.setErrorMessage(ex.getLocalizedMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		
		return response;
	}
	
}
