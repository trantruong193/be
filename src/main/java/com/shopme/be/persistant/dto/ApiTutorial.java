package com.shopme.be.persistant.dto;

import com.shopme.be.auth.AuthRequest;
import com.shopme.be.auth.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiTutorial {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Api{
        private String url;
        private String method;
        private Object object;
    }
    private Api home = new Api("https://be-ksante.up.railway.app/", "Get", null);
    private Api studentService = new Api("https://be-ksante.up.railway.app/api/v1/students", "Get,Post,Put,Delete", null);
    private Api uploadImage = new Api("https://be-ksante.up.railway.app/api/v1/files", "Post - Form data (field name: file)", null);
    private Api viewImage = new Api("https://be-ksante.up.railway.app/api/v1/files/{filename:.+}", "Get", null);
    private Api registerURL = new Api("https://be-ksante.up.railway.app/api/v1/auth/register", "Post", new RegisterRequest("Tran", "Truong", "abc@example.com", "abcdef"));
    private Api loginURL = new Api("https://be-ksante.up.railway.app/api/v1/auth/login", "Post", new AuthRequest("abc@example.com", "abcdef"));
}
