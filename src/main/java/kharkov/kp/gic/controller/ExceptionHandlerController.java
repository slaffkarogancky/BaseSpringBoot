package kharkov.kp.gic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;

import kharkov.kp.gic.exception.ResourceWasNotFoundedException;
import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ResourceWasNotFoundedException.class)
	public ResponseEntity<?> handleException(ResourceWasNotFoundedException rwnfe, HttpServletRequest request) {
		// @formatter:off
		ErrorDetail errorDetail = ErrorDetail.builder()
											 .eventDate(new Date())
											 .status(HttpStatus.NOT_FOUND.value())
											 .path(_getRequestPath(request))
											 .userMessage(rwnfe.getMessage())
											 .build();
		// @formatter:on
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> processValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {
		// @formatter:off
		String userMessage = manve.getBindingResult()
								  .getAllErrors()
								  .stream()
								  .map(ObjectError::getDefaultMessage)
								  .collect(Collectors.joining("; "));	
		ErrorDetail errorDetail = ErrorDetail.builder()
											 .eventDate(new Date())
											 .path(_getRequestPath(request))
											 .status(HttpStatus.BAD_REQUEST.value())
											 .userMessage(userMessage)
											 .build();
		// @formatter:on
		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}

	private String _getRequestPath(HttpServletRequest request) {
		String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
		return requestPath == null ? request.getRequestURI() : requestPath;
	}

}

@Data
@Builder
class ErrorDetail {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date eventDate;
	private int status;
	private String path;
	private String userMessage;
}