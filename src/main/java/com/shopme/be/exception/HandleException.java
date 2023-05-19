package com.shopme.be.exception;

import com.shopme.be.persistant.dto.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
@Slf4j
public class HandleException {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(Throwable ex){
        if(ex instanceof MyException myException){
            return new ResponseEntity<>(myException.getErrorObject(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Unauthorized
        if (ex instanceof AccessDeniedException) {
            return new ResponseEntity<>(new MyException(ErrorCode.FORBIDDEN).getErrorObject(), HttpStatus.FORBIDDEN);
        }
        // authenticated fail
        if (ex instanceof BadCredentialsException) {
            return new ResponseEntity<>(new MyException(ErrorCode.INVALID_EMAIL_PASSWORD).getErrorObject(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MyException(ErrorCode.SERVER_ERROR).getErrorObject(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new ResponseObject("Fail","Some field has error",errors));
    }
}
