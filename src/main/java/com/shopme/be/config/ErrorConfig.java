package com.shopme.be.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("error")
@Data
public class ErrorConfig {
    private Map<String,ErrorMessage> errorMessage;
}
