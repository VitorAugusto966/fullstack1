package com.jtech.backend.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(ApiError.of(404, "Not Found", ex.getMessage(), request.getDescription(false)));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
    String message = ex.getBindingResult().getFieldErrors().stream()
      .map(err -> err.getField() + ": " + err.getDefaultMessage())
      .collect(Collectors.joining("; "));
    return ResponseEntity.badRequest()
      .body(ApiError.of(400, "Bad Request", message, request.getDescription(false)));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest request) {
    return ResponseEntity.status(500)
      .body(ApiError.of(500, "Internal Server Error", ex.getMessage(), request.getDescription(false)));
  }
}
