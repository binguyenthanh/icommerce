package com.icommerce.product.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.icommerce.product.dto.CustomErrorResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(CustomErrorResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(final IllegalArgumentException ex) {
		return new ResponseEntity<>(CustomErrorResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalAccessError.class)
	public ResponseEntity<CustomErrorResponse> handleIllegalAccessError(final IllegalAccessError ex) {
		return new ResponseEntity<>(CustomErrorResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
	}
}
