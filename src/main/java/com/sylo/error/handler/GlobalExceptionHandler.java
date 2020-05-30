package com.sylo.error.handler;

import com.sylo.error.model.ApiError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dhanavenkateshgopal on 22/5/20.
 * @project sylostats
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {Exception.class,ValidatorException.class})
  public final ResponseEntity<ApiError> handleException(HttpServletRequest request,
      Exception exception) {
    log.error("Error- ", exception);
    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
