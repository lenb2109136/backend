package com.example.hethongthuongmaidientu.Config;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.hethongthuongmaidientu.model.Response;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<Response> exception(Exception e) {
		Response r= new Response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
		e.printStackTrace();
		return new ResponseEntity(r,HttpStatus.OK);
	}
	
	@ExceptionHandler(value=ConstraintViolationException.class)
	public ResponseEntity<Response> exception(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		String firstMessage = "";
		if (!violations.isEmpty()) {
		    ConstraintViolation<?> firstViolation = violations.iterator().next();
		    firstMessage = firstViolation.getMessage();
		}
		Response r = new Response(HttpStatus.BAD_REQUEST, firstMessage, null);
		return new ResponseEntity<>(r, HttpStatus.OK);

	}
	
	@ExceptionHandler(value =MissingServletRequestParameterException.class)
	public ResponseEntity<Response> MissingServletRequestParameterExceptionfunc(MissingServletRequestParameterException e){
		System.out.println(e.getClass());
		Response r= new Response(HttpStatus.BAD_REQUEST,e.getMessage(), null);
		return new ResponseEntity(r,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Response> MissingServletRequestParameterExceptionfunc(EntityNotFoundException e){
		Response r= new Response(HttpStatus.BAD_REQUEST,e.getMessage(), null);
		return new ResponseEntity(r,HttpStatus.OK);
	}
	
	@ExceptionHandler(value =MethodArgumentNotValidException.class)
	public ResponseEntity<Response> methodexxception(MethodArgumentNotValidException e) {
		Response r= new Response(HttpStatus.BAD_REQUEST,e.getBindingResult().getFieldError().getDefaultMessage(), null);
		return new ResponseEntity(r, HttpStatus.OK);
	}
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Response> MissingServletRequestParameterExceptionfunc(IOException e){
		Response r= new Response(HttpStatus.BAD_REQUEST,e.getMessage(), null);
		return new ResponseEntity(r,HttpStatus.OK);
	}
}
