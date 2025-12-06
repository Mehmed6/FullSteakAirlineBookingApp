package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.LoginRequest;
import com.doganmehmet.AirlineBookingApp.dto.request.RegisterRequest;
import com.doganmehmet.AirlineBookingApp.dto.response.LoginResponse;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.entity.Role;
import com.doganmehmet.AirlineBookingApp.entity.User;
import com.doganmehmet.AirlineBookingApp.enums.AuthMethod;
import com.doganmehmet.AirlineBookingApp.exception.BadRequestException;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.RoleRepository;
import com.doganmehmet.AirlineBookingApp.repository.UserRepository;
import com.doganmehmet.AirlineBookingApp.security.JwtUtils;
import com.doganmehmet.AirlineBookingApp.service.AuthService;
import com.doganmehmet.AirlineBookingApp.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final EmailNotificationService emailNotificationService;

    @Override
    public Response<?> register(RegisterRequest registerRequest)
    {
        log.info("Inside register()");

        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new BadRequestException("Email already in use");

        List<Role> userRoles;

        if (registerRequest.getRoles() != null && !registerRequest.getRoles().isEmpty()) {
            userRoles = registerRequest.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.toUpperCase(Locale.ENGLISH))
                            .orElseThrow(() -> new NotFoundException("Role is not found: " + role)))
                    .toList();
        } else {
            var defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("Role CUSTOMER DOESN'T EXISTS"));

            userRoles = List.of(defaultRole);
        }

        var user = new User();
        user.setName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setRoles(userRoles);
        user.setAuthMethod(AuthMethod.LOCAL);
        user.setActive(true);

        var savedUser = userRepository.save(user);

        emailNotificationService.sendWelcomeEmail(savedUser);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("user registered successfully")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest)
    {
        log.info("Inside login()");
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.isActive())
            throw new NotFoundException("Acount Not Active, Please reach Out to Customer Care...");

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new BadRequestException("Invalid Password");

        var token = jwtUtils.generateToken(user.getEmail());
        var roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        var loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roles);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(loginResponse)
                .build();
    }
}
