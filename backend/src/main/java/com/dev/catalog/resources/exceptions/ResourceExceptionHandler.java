package com.dev.catalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dev.catalog.services.exceptions.DatabaseException;
import com.dev.catalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> notFoundException(ResourceNotFoundException exception, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found!");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException exception, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Database exception!");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(error);
	}
}
