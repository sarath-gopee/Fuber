package com.sarath.exception;

import java.net.http.HttpHeaders;
import java.util.ArrayList;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sarath.exception.ErrorResponse;

@ControllerAdvice
public class customExceptionHandler {
    
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Object> handleHeaderException(Exception ex)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Bad Request.Check URI Parameters", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	 List<String> details = new ArrayList<>();
         details.add(ex.getLocalizedMessage());
         ErrorResponse error = new ErrorResponse("Invalid parameters", details);
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
 @ExceptionHandler(ClassNotFoundException.class)
    public final ResponseEntity<Object> handleNoCabException(ClassNotFoundException ex)
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("No Cabs are Available at the moment", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(cabNotFoundException.class)
 public final ResponseEntity<Object> handleCabNotFoundException(cabNotFoundException ex)
 {
     List<String> details = new ArrayList<>();
     details.add(ex.getLocalizedMessage());
     ErrorResponse error = new ErrorResponse("Sorry the requested cab is unavailble right now. Please check again", details);
     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
 }

 
 

}
