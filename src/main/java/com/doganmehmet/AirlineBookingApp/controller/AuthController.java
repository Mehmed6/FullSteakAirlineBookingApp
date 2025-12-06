package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.request.LoginRequest;
import com.doganmehmet.AirlineBookingApp.dto.request.RegisterRequest;
import com.doganmehmet.AirlineBookingApp.dto.response.LoginResponse;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Response<?>> register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<Response<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
