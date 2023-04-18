package com.shopme.be.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Authentication {
    private String token;
}
