package com.shopme.be.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException{
    private ErrorObject errorObject;

    public MyException(String errorCode) {
        super(errorCode);
        errorObject = ErrorObject
                .builder().errorCode(errorCode)
                .errorMessage(ErrorLoader.getErrorMessage(errorCode))
                .build();
    }
}
