package com.yoga.medledger.exception;

import com.yoga.medledger.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ApiResponse handleEmailExists(EmailAlreadyExistsException ex) {
    return new ApiResponse(false, ex.getMessage());
  }
}