package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.response.UserDTO;
import com.doganmehmet.AirlineBookingApp.dto.update.UserUpdateDTO;
import com.doganmehmet.AirlineBookingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<Response<?>> updateMyAccount(@RequestBody UserUpdateDTO userUpdateDTO)
    {
        return ResponseEntity.ok(userService.updateMyAccount(userUpdateDTO));
    }

    @GetMapping("/pilots")
    @PreAuthorize("hasAnyRole('ADMIN', 'PILOT')")
    public ResponseEntity<Response<List<UserDTO>>> getAllPilots()
    {
        return ResponseEntity.ok(userService.getAllPilots());
    }

    @GetMapping("/me")
    public ResponseEntity<Response<UserDTO>> getAccountDetails()
    {
        return ResponseEntity.ok(userService.getAccountDetails());
    }
}
