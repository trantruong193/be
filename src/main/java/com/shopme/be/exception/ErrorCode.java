package com.shopme.be.exception;

public interface ErrorCode {
    String DUPLICATE_EMAIL = "ERROR_01";
    String INVALID_EMAIL_PASSWORD = "ERROR_02";
    String INVALID_INFORMATION = "ERROR_03";
    String SERVER_ERROR = "ERROR_500";
    String FORBIDDEN = "ERROR_401";
}
