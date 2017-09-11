package kharkov.kp.gic.controller;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;

import kharkov.kp.gic.exception.EntityWasNotFoundedException;
import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	// Обрабатываем пользовательское исключение, не предусмотренное в ResponseEntityExceptionHandler
	@ExceptionHandler(EntityWasNotFoundedException.class)
	public ResponseEntity<?> handleEntityWasNotFoundedException(EntityWasNotFoundedException rwnfe, HttpServletRequest request) {
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
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException cve, HttpServletRequest request) {
		// @formatter:off
		String message = cve.getConstraintViolations()
						    .stream()
						    .map(cv -> String.format("Field=\"%s\", error=\"%s\"", cv.getPropertyPath(), cv.getMessage()))
						    .collect(Collectors.joining("; "));	
		ErrorDetail errorDetail = ErrorDetail.builder()
											 .eventDate(new Date())
											 .status(HttpStatus.BAD_REQUEST.value())
											 .path(_getRequestPath(request))
											 .userMessage(message)
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