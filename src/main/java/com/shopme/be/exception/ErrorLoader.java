package com.shopme.be.exception;

import com.shopme.be.config.ErrorConfig;
import com.shopme.be.config.ErrorMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class ErrorLoader {
    private ErrorConfig errorConfig;
    private static Map<String, ErrorMessage> errorMessageMap;

    @Autowired
    public ErrorLoader(ErrorConfig errorConfig){
        this.errorConfig = errorConfig;
        errorMessageMap = errorConfig.getErrorMessage();
    }
    public static ErrorMessage getErrorMessage(String errorCode){
        return errorMessageMap.get(errorCode);
    }
}
