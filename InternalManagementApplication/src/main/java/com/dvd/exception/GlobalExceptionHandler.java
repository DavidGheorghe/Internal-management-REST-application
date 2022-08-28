package com.dvd.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dvd.DTO.CustomExceptionDetails;


/**
* Exception Handler Controller for all exceptions.
*
* @author David Gheorghe
*/
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RemovePrivilegeFromUserException.class)
	public ResponseEntity<CustomExceptionDetails> handleRemovePrivilegeFromUserException(RemovePrivilegeFromUserException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SamePasswordException.class)
	public ResponseEntity<CustomExceptionDetails> handleSamePasswordException(SamePasswordException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SameUsernameException.class)
	public ResponseEntity<CustomExceptionDetails> handleSameUsernameException(SameUsernameException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UsernameTakenException.class)
	public ResponseEntity<CustomExceptionDetails> handleUsernameTakenException(UsernameTakenException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerCredentialTakenException.class)
	public ResponseEntity<CustomExceptionDetails> handleCustomerCredentialTakenException(CustomerCredentialTakenException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UniqueEntryException.class)
	public ResponseEntity<CustomExceptionDetails> handleUniqueEntryException(UniqueEntryException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<CustomExceptionDetails> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<CustomExceptionDetails> handleBadCredentialsException(BadCredentialsException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<CustomExceptionDetails> handleLoginFailedException(LoginFailedException exception, WebRequest webRequest) {
		CustomExceptionDetails exceptionDetails = new CustomExceptionDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(exceptionDetails, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}	
}
