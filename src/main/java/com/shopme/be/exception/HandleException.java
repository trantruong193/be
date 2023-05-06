package com.shopme.be.exception;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
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
}
