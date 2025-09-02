package com.ihab.e_commerce.controller.auth;

import com.ihab.e_commerce.controller.request.AuthenticationRequest;
import com.ihab.e_commerce.controller.response.GlobalSuccessResponse;
import com.ihab.e_commerce.controller.request.RegisterRequest;
import com.ihab.e_commerce.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GlobalSuccessResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<GlobalSuccessResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
