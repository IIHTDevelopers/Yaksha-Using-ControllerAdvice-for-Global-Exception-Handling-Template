package com.yaksha.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle ValidationException
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleValidationException(ValidationException ex) {
		return new ResponseEntity<>("Validation error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// Handle generic exceptions
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
