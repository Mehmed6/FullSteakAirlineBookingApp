package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.request.LoginRequest;
import com.doganmehmet.AirlineBookingApp.dto.request.RegisterRequest;
import com.doganmehmet.AirlineBookingApp.dto.response.LoginResponse;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;

public interface AuthService {

    Response<?> register(RegisterRequest registerRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
