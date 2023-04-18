package com.shopme.be.exception;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class MyException extends RuntimeException{
    private ErrorObject errorObject;
    @Autowired
    ErrorLoader errorLoader;

    public MyException(String errorCode) {
        super(errorCode);
        errorObject = ErrorObject
                .builder().errorCode(errorCode)
                .errorMessage(ErrorLoader.getErrorMessage(errorCode))
                .build();
    }
}
