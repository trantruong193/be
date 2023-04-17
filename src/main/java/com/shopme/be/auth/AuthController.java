package com.shopme.be.auth;

import com.shopme.be.persistant.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<ResponseObject> register (@RequestBody RegisterRequest request){
        Authentication authentication = authService.register(request);
        return ResponseEntity.status(200).body(
                new ResponseObject("Success","Register new account successfully",authentication)
        );
    }
    @PostMapping("login")
    public ResponseEntity<ResponseObject> login (@RequestBody AuthRequest request){
        Authentication authentication = authService.authenticate(request);
        return ResponseEntity.status(200).body(
                new ResponseObject("Success","Login successfully",authentication)
        );
    }
}
