package com.shopme.be.exception;

import com.shopme.be.config.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorObject {
    private String errorCode;
    private ErrorMessage errorMessage;
}
