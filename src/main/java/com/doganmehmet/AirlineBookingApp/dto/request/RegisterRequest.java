package com.doganmehmet.AirlineBookingApp.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    private List<String> roles;
}
